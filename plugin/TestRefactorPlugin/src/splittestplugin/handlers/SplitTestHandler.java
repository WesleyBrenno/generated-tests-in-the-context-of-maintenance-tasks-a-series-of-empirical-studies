package splittestplugin.handlers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.PerformRefactoringOperation;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import visitors.AssertVisitor;
import visitors.AssignmentVisitor;
import visitors.MethodDeclarationVisitor;
import visitors.MethodInvocationStatementVisitor;
import visitors.SimpleNameVisitor;
import visitors.TryStatementVisitor;
import visitors.VariableDeclarationStatementVisitor;

public class SplitTestHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String projectName = "<Eclipse Project name>";

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IJavaProject jproject = JavaCore.create(iProject);

		try {
			IProject project = jproject.getProject();
			IFolder folder = project.getFolder("test");
			IPackageFragmentRoot packageFragmenteRoot = jproject.getPackageFragmentRoot(folder);

			IPackageFragment packageFragment = packageFragmenteRoot.createPackageFragment("<Test Class Package>", true, null);

			ICompilationUnit unit = packageFragment.getCompilationUnit("<Test Class Name>"+".java");

			ASTParser parser = ASTParser.newParser(AST.JLS14);
			parser.setResolveBindings(true);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setBindingsRecovery(true);
			parser.setSource(unit);

			CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);

			MethodDeclarationVisitor methodVisitor = new MethodDeclarationVisitor();
			astRoot.accept(methodVisitor);

			List<MethodDeclaration> methods = methodVisitor.getMethods();

			AST ast = astRoot.getAST();
			ASTRewrite rewriter = ASTRewrite.create(ast);

			TypeDeclaration typeDecl = (TypeDeclaration) astRoot.types().get(0);
			ListRewrite listMethodRewrite = rewriter.getListRewrite(typeDecl,
					TypeDeclaration.BODY_DECLARATIONS_PROPERTY);

			for (int i = methods.size() - 1; i >= 0; i--) {
				MethodDeclaration testMethod = methods.get(i);
				String testName = testMethod.getName().toString();

				AssertVisitor assertVisitor = new AssertVisitor();

				TryStatementVisitor tryStatementeVisitor = new TryStatementVisitor();
				
				testMethod.accept(assertVisitor);
				testMethod.accept(tryStatementeVisitor);
				
				List<ASTNode> assertNodes = new ArrayList<ASTNode>();
				
				assertNodes.addAll(assertVisitor.getMethods());
				assertNodes.addAll(tryStatementeVisitor.getTryStatements());
								
				Iterator<ASTNode> assertIt = assertNodes.iterator();
//							
//				//Abordagem 1 
////				int newMethodIndex = 0;
//				
				// Abordagem2
				MethodDeclaration newMethod = astRoot.getAST().newMethodDeclaration();
				newMethod.setBody(astRoot.getAST().newBlock());
				newMethod.setConstructor(false);
				newMethod.setName(astRoot.getAST().newSimpleName(testName + "_reordered"));

				while (assertIt.hasNext()) {
					Set<ASTNode> newTestNodes = new HashSet<ASTNode>();

					ASTNode assertStatement = assertIt.next();
					
					if(assertStatement instanceof MethodInvocation) {
						newTestNodes.add(assertStatement.getParent());
					} else {
						newTestNodes.add(assertStatement);
					}

					SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor();
					assertStatement.accept(simpleNameVisitor);
					
					List<SimpleName> simpleNames = simpleNameVisitor.getNames();

					int index = 0;

					while (index < simpleNames.size()) {
						SimpleName simpleName = simpleNames.get(index);
						
						int limitPosition = simpleName.getStartPosition();
						
						String variableName = simpleName.toString();

						VariableDeclarationStatementVisitor declarations = new VariableDeclarationStatementVisitor(variableName, limitPosition);
						testMethod.accept(declarations);

						AssignmentVisitor assigmentVisitor = new AssignmentVisitor(variableName, limitPosition);
						testMethod.accept(assigmentVisitor);
						
						MethodInvocationStatementVisitor methodInvocationVisitor = new MethodInvocationStatementVisitor(variableName, limitPosition);
						testMethod.accept(methodInvocationVisitor);
						
						List<ASTNode> nodes = new ArrayList();
						nodes.addAll(declarations.getDeclarations());
						nodes.addAll(assigmentVisitor.getAssignments());
						nodes.addAll(methodInvocationVisitor.getMethodInvocations());
						
						for (ASTNode node : nodes) {
							newTestNodes.add(node);

							SimpleNameVisitor simpleNameVisitor2 = new SimpleNameVisitor();
							node.accept(simpleNameVisitor2);

							for (SimpleName name2 : simpleNameVisitor2.getNames()) {
								if (!simpleNames.contains(name2)) {
									simpleNames.add(name2);
								}
							}
						}
						index++;
					}

					List<ASTNode> nodesOrdered = newTestNodes.stream().collect(Collectors.toList());
					Collections.sort(nodesOrdered, (o1, o2) -> o1.getStartPosition() - o2.getStartPosition());

					if (nodesOrdered.size() > 0) {
						// Abordagem 1
//						MethodDeclaration newMethod = astRoot.getAST().newMethodDeclaration();
//						newMethod.setBody(astRoot.getAST().newBlock());
//						newMethod.setConstructor(false);
//						newMethod.setName(astRoot.getAST().newSimpleName(testName + "_" + newMethodIndex));

						Block block = newMethod.getBody();

						ListRewrite listBlockRewrite = rewriter.getListRewrite(block, Block.STATEMENTS_PROPERTY);

						for (ASTNode node : nodesOrdered) {
							listBlockRewrite.insertLast(node, null);
						}

						// Abordagem 1
//						listMethodRewrite.insertLast(newMethod, null);					
//						newMethodIndex++;						
					}
				}

				// Abordagem 2
				listMethodRewrite.insertLast(newMethod, null);
				// ---------------------------------

				listMethodRewrite.remove(testMethod, null);
			}
			
			TextEdit edits = rewriter.rewriteAST();

			// apply the text edits to the compilation unit
			Document document = new Document(unit.getSource());

			edits.apply(document);

			// this is the code for adding statements
			unit.getBuffer().setContents(document.get());

	        unit.applyTextEdit(edits, new NullProgressMonitor());
	        
	        //----------------------------
	        
			parser.setSource(unit);

			astRoot = (CompilationUnit) parser.createAST(null);
			
			typeDecl = (TypeDeclaration) astRoot.types().get(0);
			
			for (int index = 0; index < typeDecl.getMethods().length; index ++) {
				MethodDeclaration methodReodered = typeDecl.getMethods()[index];
				if(methodReodered.getName().toString().contains("reordered")) {
					String methodName = methodReodered.getName().toString().replace("reordered", "");

					Block block = methodReodered.getBody();

					int initial = 0;

					int length = 0;

					int methodExtractedIndex = 0;

					List statements = block.statements();
					
					for (int i = statements.size() -1; i >= 0; i--) {
						Statement statement = (Statement) statements.get(i);
						
						boolean isTestInvocationStatement = statement.toString().matches("test[0-9]*[0-9]*.*\\n");
						
						boolean isAssertStatement = statement.toString().matches(AssertVisitor.ASSERT_REGEX);
						
						boolean isLastStatement = i == 0;
						
						if(!isTestInvocationStatement || isLastStatement ) {
							if(length == 0) {
								length = statement.getStartPosition() + statement.getLength();
							} else {
								if (isLastStatement || isAssertStatement) {
									if(isLastStatement && !isTestInvocationStatement) {
										initial = statement.getStartPosition();
									}
									ExtractMethodRefactoring refactoring = new ExtractMethodRefactoring(unit, initial, length - initial);
									refactoring.setReplaceDuplicates(false);
									refactoring.setMethodName(methodName + methodExtractedIndex);
									refactoring.setVisibility(1);
									refactoring.checkInitialConditions(new NullProgressMonitor());							
									refactoring.setDestination(0);
									checkPreconditions(unit, refactoring);
									performRefactor(refactoring);							
									
									length = 0;
									methodExtractedIndex++;
									
									parser.setSource(unit);

									astRoot = (CompilationUnit) parser.createAST(null);
									
									typeDecl = (TypeDeclaration) astRoot.types().get(0);
									
									methodReodered = typeDecl.getMethods()[index];
									
									block = methodReodered.getBody();
									
									statements = block.statements();
									
									i = statements.size() -1;
									
									
								}
								initial = statement.getStartPosition();
							}
						}
					}
					
//					parser.setSource(unit);
//
//					astRoot = (CompilationUnit) parser.createAST(null);
//					
//					typeDecl = (TypeDeclaration) astRoot.types().get(0);
				}
			}
			
			ast = astRoot.getAST();
			
			rewriter = ASTRewrite.create(ast);
			
			listMethodRewrite = rewriter.getListRewrite(typeDecl,
					TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
			
			MethodDeclaration[] methodDeclarations = typeDecl.getMethods();  
			
			for (int i = methodDeclarations.length - 1; i >= 0; i--  ) {
				MethodDeclaration testMethod = methodDeclarations[i];
				if(testMethod.getName().toString().contains("reordered")) 
					listMethodRewrite.remove(testMethod, null);
				else {
					final NormalAnnotation testAnnotation = testMethod.getAST().newNormalAnnotation();
					testAnnotation.setTypeName(testMethod.getAST().newName("Test"));
					ListRewrite listAnnotationRewrite = rewriter.getListRewrite(testMethod, MethodDeclaration.MODIFIERS2_PROPERTY );
					listAnnotationRewrite.insertAt(testAnnotation, 0, null);
				}
			}
			
			edits = rewriter.rewriteAST();

			// apply the text edits to the compilation unit
			document = new Document(unit.getSource());

			edits.apply(document);

			// this is the code for adding statements
			unit.getBuffer().setContents(document.get());

	        unit.applyTextEdit(edits, new NullProgressMonitor());
			
		} catch (CoreException e) {
			projectName = "CoreException";
			e.printStackTrace();
		} catch (Exception e) {
			projectName = "Exception";
			e.printStackTrace();
		}

		MessageDialog.openInformation(window.getShell(), "Plugin", projectName);

		return null;
}
	
	protected void checkPreconditions(final ICompilationUnit unit, final Refactoring refactoring) throws Exception {
		IProgressMonitor pm = new NullProgressMonitor();
		checkPreconditions(refactoring, pm).isOK();
	}

	protected RefactoringStatus checkPreconditions(Refactoring refactoring, IProgressMonitor pm) throws CoreException {
		CheckConditionsOperation op = new CheckConditionsOperation(refactoring, getCheckingStyle());
		op.run(pm);
		return op.getStatus();
	}

	protected void performRefactor(Refactoring refactoring) throws CoreException {
		PerformRefactoringOperation op = new PerformRefactoringOperation(refactoring, getCheckingStyle());
		JavaCore.run(op, new NullProgressMonitor());
	}

	protected int getCheckingStyle() {
		return CheckConditionsOperation.ALL_CONDITIONS;
	}
	
	protected URLClassLoader getProjectClassLoader(IJavaProject javaProject) throws CoreException, MalformedURLException {
		String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
		List<URL> urlList = new ArrayList<URL>();
		for (int i = 0; i < classPathEntries.length; i++) {
		 String entry = classPathEntries[i];
		 IPath path = new Path(entry);
		 URL url = url = path.toFile().toURI().toURL();
		 urlList.add(url);
		}
		
		ClassLoader parentClassLoader = javaProject.getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);
		
		return classLoader;
	}
}
