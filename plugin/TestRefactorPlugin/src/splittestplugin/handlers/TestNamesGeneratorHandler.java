package splittestplugin.handlers;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.osgi.container.Module;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.evosuite.assertion.PrimitiveAssertion;
import org.evosuite.coverage.exception.ExceptionCoverageTestFitness;
import org.evosuite.coverage.io.input.InputCoverageGoal;
import org.evosuite.coverage.io.input.InputCoverageTestFitness;
import org.evosuite.coverage.io.output.OutputCoverageGoal;
import org.evosuite.coverage.io.output.OutputCoverageTestFitness;
import org.evosuite.coverage.method.MethodCoverageTestFitness;
import org.evosuite.coverage.method.MethodNoExceptionCoverageTestFitness;
import org.evosuite.junit.naming.methods.CoverageGoalTestNameGenerationStrategy;
import org.evosuite.shaded.org.objectweb.asm.Type;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.statements.ConstructorStatement;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testcase.statements.NullStatement;
import org.evosuite.testcase.statements.PrimitiveExpression;
import org.evosuite.testcase.statements.StringPrimitiveStatement;
import org.evosuite.testcase.statements.numeric.BooleanPrimitiveStatement;
import org.evosuite.testcase.statements.numeric.IntPrimitiveStatement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.utils.generic.GenericConstructor;
import org.evosuite.utils.generic.GenericMethod;

public class TestNamesGeneratorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String projectName = "<Eclipse Project Name>";

		int suiteSize = 0;

		IProject exampleProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		IJavaProject jproject = JavaCore.create(exampleProject);

		IProject project = jproject.getProject();
		IFolder folder = project.getFolder("test");
		IPackageFragmentRoot packageFragmenteRoot = jproject.getPackageFragmentRoot(folder);

		IPackageFragment packageFragment;
		ICompilationUnit unit = null;
		TypeDeclaration typeDecl = null;
		AST ast;
		ASTRewrite rewriter = null;
		CompilationUnit astRoot = null;

		try {
			packageFragment = packageFragmenteRoot.createPackageFragment("<Test Class Package>", true, null);
			unit = packageFragment.getCompilationUnit("<Test Class Name>" + ".java");
			ASTParser parser = ASTParser.newParser(AST.JLS14);
			parser.setResolveBindings(true);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setBindingsRecovery(true);
			parser.setSource(unit);

			astRoot = (CompilationUnit) parser.createAST(null);

			ast = astRoot.getAST();
			rewriter = ASTRewrite.create(ast);

			typeDecl = (TypeDeclaration) astRoot.types().get(0);
			suiteSize = typeDecl.getMethods().length;
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean randoop = true;
		Set[] allCoveredGoals = null;
		HashMap<Integer, String>[] allStatemetns = null;
		HashMap<Integer, String> methods = null;

		String targetClassName = "<CUT Package>.<CUT Name>";
		String projectPath = "<Project Path>";
		String classesPath = projectPath + "<Classes Path>";

		List<TestCase> tests = new ArrayList<>();

		Class loadedClass = new Object().getClass();

		try {
			allCoveredGoals = readCoveredGoalsFile(projectPath + "covered.goals", suiteSize);
			allStatemetns = readStatementFile(projectPath + "testsStatements.txt", suiteSize);
			methods = readMethodCallsFile(projectPath + "methods.txt");
			URLClassLoader classLoader = new URLClassLoader(new URL[] { new File(classesPath).toURI().toURL() });
			loadedClass = Class.forName(targetClassName, true, classLoader);
	//		classLoader.close();
		} catch (IOException e) {
			fail("File read error: '" + e.getMessage());
		} catch (ClassNotFoundException e) {
			fail("Classe not found: '" + e.getMessage());
		}

//		loadClasses(classesPath, "", classesPath);

		for (int it = 0; it < allCoveredGoals.length; it++) {
			TestCase test = new DefaultTestCase();

			Set<String> testGoals = allCoveredGoals[it];
			Iterator<String> iterator = testGoals.iterator();

			System.out.println(">>>>>>>>>>>>  TEST ");

			while (iterator.hasNext()) {
				String coveredGoal = iterator.next();
				String[] goalsAttributes = coveredGoal.split(" ");
				System.out.println(coveredGoal);

				switch (goalsAttributes[0]) {
				case "[METHOD]":
					MethodCoverageTestFitness methodCoverage = new MethodCoverageTestFitness(goalsAttributes[1],
							goalsAttributes[2]);
					test.addCoveredGoal(methodCoverage);
					break;
				case "[INPUT]":
					// System.out.println(coveredGoal);
					String methodName = "";

					for (int i = 2; i < (2 + goalsAttributes.length - 4); i++) {
						methodName += goalsAttributes[i];
					}

					int argIndex = Integer.valueOf(goalsAttributes[goalsAttributes.length - 2]);

					String inputType = goalsAttributes[3 + argIndex].replace("/", ".");

					String inputValue = goalsAttributes[goalsAttributes.length - 1];

					InputCoverageGoal inputGoal = new InputCoverageGoal(goalsAttributes[1], methodName, argIndex,
							Type.getType(inputType), inputValue);
					InputCoverageTestFitness inputCoverage = new InputCoverageTestFitness(inputGoal);
					test.addCoveredGoal(inputCoverage);
					break;
				case "[OUTPUT]":
					String outputType = goalsAttributes[2].split("\\)")[0].replace("/", ".");
					OutputCoverageGoal outputGoal = new OutputCoverageGoal(goalsAttributes[1], goalsAttributes[2],
							Type.getType(outputType), goalsAttributes[3]);
					OutputCoverageTestFitness outputCoverage = new OutputCoverageTestFitness(outputGoal);
					test.addCoveredGoal(outputCoverage);
					break;
				case "[EXCEPTION]":
					ExceptionCoverageTestFitness exceptionCoverage;
					ExceptionCoverageTestFitness.ExceptionType exceptionType;
					if (goalsAttributes[4].equals("EXPLICIT")) {
						exceptionType = ExceptionCoverageTestFitness.ExceptionType.EXPLICIT;
					} else if (goalsAttributes[4].equals("IMPLICIT")) {
						exceptionType = ExceptionCoverageTestFitness.ExceptionType.IMPLICIT;
					} else {
						exceptionType = ExceptionCoverageTestFitness.ExceptionType.DECLARED;
					}
					try {
						exceptionCoverage = new ExceptionCoverageTestFitness(goalsAttributes[1], goalsAttributes[2],
								loadClass(classesPath, goalsAttributes[3]), exceptionType);
						test.addCoveredGoal(exceptionCoverage);
					} catch (ClassNotFoundException | IllegalArgumentException | IOException e) {
						try {
							exceptionCoverage = new ExceptionCoverageTestFitness(goalsAttributes[1], goalsAttributes[2],
									loadClass(classesPath, goalsAttributes[3]), exceptionType);
							test.addCoveredGoal(exceptionCoverage);
						} catch (IllegalArgumentException | ClassNotFoundException | IOException e1) {
							e1.printStackTrace();
						}
					}
					break;
				case "[METHODNOEX]":
					MethodNoExceptionCoverageTestFitness methodNoExceptionCoverage = new MethodNoExceptionCoverageTestFitness(
							goalsAttributes[1], goalsAttributes[2]);
					test.addCoveredGoal(methodNoExceptionCoverage);
					break;
				default:
					break;
				}
			}

			HashMap<Integer, String> statements = allStatemetns[it];
			HashMap<String, VariableReference> variableReferences = new HashMap<>();

			if (!statements.isEmpty()) {
				List<Integer> keys = new ArrayList<>(statements.keySet());
				Collections.sort(keys);

				for (int key : keys) {

					String method = methods.get(key);
					String statement = statements.get(key);

					if (method != null) {
						if (method.contains("<init>")) {
							System.out.println("Constructor " + statement);
							String[] tokens = statement.split(" ");
							String identifier = tokens[1];
							String type = tokens[4];

							GenericConstructor genericConstructor = null;
							ArrayList<VariableReference> references = new ArrayList<>();

							String[] arguments = method.substring(method.indexOf("(") + 2, method.indexOf(")") - 1)
									.replace(",", "").split(" ");

							Class[] argsClass = new Class[arguments.length];

							try {
								if (arguments.length == 1 && arguments[0].equals("")) {
									genericConstructor = new GenericConstructor(loadedClass.getDeclaredConstructor(),
											loadedClass);
								} else {
									for (int i = 0; i < arguments.length; i++) {
										System.out.println(arguments[i]);
										if (arguments[i].contains("["))
											argsClass[i] = loadClass(classesPath, arguments[i]);
										else
											argsClass[i] = loadClass(classesPath,
													Type.getType(arguments[i]).getClassName());

										VariableReference argReference = test
												.addStatement(new NullStatement(test, argsClass[i]));
										references.add(argReference);
									}
									genericConstructor = new GenericConstructor(
											loadedClass.getDeclaredConstructor(argsClass), loadedClass);
								}
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

							VariableReference variable = test
									.addStatement(new ConstructorStatement(test, genericConstructor, references));
							variableReferences.put(identifier, variable);
						} else if (statement.contains(" = ")) {
							System.out.println("Method " + statement);
							String[] tokens = statement.split(" ");

							String identifier = tokens[1];
							String methodName = method.substring(0, method.indexOf("("));
							String[] arguments = method.substring(method.indexOf("(") + 2, method.indexOf(")") - 1)
									.replace(",", "").split(" ");
							Class[] argsClass = new Class[arguments.length];

							String targetName = tokens[3].split("\\.")[0].trim();

							System.out.println(targetName);

							VariableReference targetReference = variableReferences.get(targetName);

							GenericMethod genericMethod = null;
							ArrayList<VariableReference> references = new ArrayList<>();

							try {
								if (arguments.length == 1 && arguments[0].equals("")) {
									genericMethod = new GenericMethod(loadedClass.getMethod(methodName), loadedClass);
								} else {
									for (int i = 0; i < arguments.length; i++) {
										System.out.println(arguments[i]);
										if (arguments[i].contains("["))
											argsClass[i] = loadClass(classesPath, arguments[i]);
										else {
											String className = Type.getType(arguments[i]).getClassName();
											argsClass[i] = loadClass(classesPath, className);
										}
										VariableReference argReference = test
												.addStatement(new NullStatement(test, argsClass[i]));
										references.add(argReference);
									}
									genericMethod = new GenericMethod(loadedClass.getMethod(methodName, argsClass),
											loadedClass);
								}
							}
							catch (ClassNotFoundException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}

							VariableReference variable = test.addStatement(
									new MethodStatement(test, genericMethod, targetReference, references));
							variableReferences.put(identifier, variable);
							System.out.println(identifier);
						}
					}
					if (randoop) {
						if (statement.contains("assertTrue") || statement.contains("assertFalse")) {
							System.out.println("AssertTrue " + statement);
							String[] tokens = statement.split(",");

							String expression = tokens[1].trim().replace(";", "").replace(")", "");

							String[] terms = expression.split(" ");

							VariableReference leftOperand = null;
							VariableReference rightOperand = null;

							String term0 = terms[0];

							if (term0.matches("true")) {
								leftOperand = test.addStatement(new BooleanPrimitiveStatement(test, true));
							} else if (term0.matches("false")) {
								leftOperand = test.addStatement(new BooleanPrimitiveStatement(test, false));
							} else if (term0.matches("\"")) {
								leftOperand = test
										.addStatement(new StringPrimitiveStatement(test, term0.replace("\"", "")));
							} else if (term0.matches("[0-9]*")) {
								leftOperand = test
										.addStatement(new IntPrimitiveStatement(test, Integer.valueOf(term0)));
							} else {
								leftOperand = variableReferences.get(term0);
							}

							String term1 = terms[2];

							if (term1.matches("true")) {
								rightOperand = test.addStatement(new BooleanPrimitiveStatement(test, true));
							} else if (term1.matches("false")) {
								rightOperand = test.addStatement(new BooleanPrimitiveStatement(test, false));
							} else if (term1.matches("\"")) {
								rightOperand = test
										.addStatement(new StringPrimitiveStatement(test, term0.replace("\"", "")));
							} else if (term1.matches("[0-9]*")) {
								rightOperand = test
										.addStatement(new IntPrimitiveStatement(test, Integer.valueOf(term1)));
							} else {
								rightOperand = variableReferences.get(term1);
							}

							if (leftOperand != null && rightOperand != null) {
								VariableReference boolean0 = test.addStatement(new BooleanPrimitiveStatement(test));

								PrimitiveExpression primitiveExpression = new PrimitiveExpression(test, boolean0,
										leftOperand, PrimitiveExpression.Operator.toOperator(terms[1]), rightOperand);

								VariableReference expressionStatment = test.addStatement(primitiveExpression);

								PrimitiveAssertion assertion = new PrimitiveAssertion();
								assertion.setSource(expressionStatment);
								assertion.setValue(true);

								test.getStatement(expressionStatment.getStPosition() + 1).addAssertion(assertion);
							}
						}
					} else {
						System.out.println(statement);
						String variableIdentifier = "";

						if (statement.contains("assertTrue") || statement.contains("assertFalse")) {
							variableIdentifier = statement.substring(statement.indexOf("(") + 1,
									statement.indexOf(")"));
						} else if (statement.contains("assertEquals")) {
							int beginIndex = statement.indexOf(",") + 2;
							int endIndex = statement.indexOf(")", beginIndex);
							variableIdentifier = statement.substring(beginIndex, endIndex);
						}
						System.out.println(variableIdentifier);
						VariableReference boolean0 = variableReferences.get(variableIdentifier);

						if (boolean0 != null) {
							PrimitiveAssertion assertion = new PrimitiveAssertion();
							assertion.setSource(boolean0);
//	                            if(boolean0.getType().getTypeName().equals("boolean")){
//	                                assertion.setValue(true);
//	                            } else {
							assertion.setValue(true);
							// }
							test.getStatement(boolean0.getStPosition()).addAssertion(assertion);
						}
					}
				}
			}
//	            for (String statement : statements.values()) {
//	                statement = statement.replace(";", "");
			//
////	                if (statement.contains("[") && statement.contains(" new ")) {
////	                    System.out.println("Array Statement " + statement);
////	                    //TODO Array Statment
////	                } else if (statement.matches(".*\\[.*].*")) {
////	                    System.out.println("Array Index " + statement);
////	                    //TODO Array Index
////	                } else if (statement.matches(".* = ([0-9]+(.[0-9]+)?(f)?(l|L)?|.[0-9]+f).*")) {
////	                    System.out.println("Primitive number " + statement);
////	                    //TODO Primitive number
////	                } else if (statement.matches(".*String.* = \".*\".*")) {
////	                    System.out.println("String " + statement);
////	                    //TODO String
//	                if (statement.contains(" new ")) {
//	                    System.out.println("Constructor " + statement);
//	                    String[] tokens = statement.split(" ");
			//
//	                    String identifier = tokens[1];
//	                    String type = tokens[4];
			//
//	                    System.out.println(identifier);
			//
//	                    GenericConstructor genericConstructor = null;
//	                    ArrayList<VariableReference> references = new ArrayList<>();
//	                    try {
//	                        if (type.contains("FixedOrderComparator()")) {
//	                            genericConstructor = new GenericConstructor(loadedClass.getDeclaredConstructor(), loadedClass);
//	                        } else if (type.matches(".*new collections.comparators.FixedOrderComparator\\(.+\\)")) {
//	                            String paramIdentifier = type.substring(tokens[4].indexOf("("), type.indexOf(")"));
//	                            VariableReference parameter = variableReferences.get(paramIdentifier);
//	                            genericConstructor = new GenericConstructor(loadedClass.getDeclaredConstructor(parameter.getVariableClass()), loadedClass);
//	                            references.add(parameter);
//	                        } else {
//	                            genericConstructor = new GenericConstructor(Object.class.getDeclaredConstructor(), Object.class);
//	                        }
//	                    } catch (NoSuchMethodException e) {
//	                        e.printStackTrace();
//	                    }
			//
//	                    VariableReference variable = test.addStatement(new ConstructorStatement(test, genericConstructor, references));
			//
//	                    variableReferences.put(identifier, variable);
			//
//	                } else if (statement.contains("assertTrue") || statement.contains("assertFalse")) {
//	                    System.out.println("AssertTrue " + statement);
//	                    String[] tokens = statement.split(",");
			//
//	                    String expression = tokens[1].trim().replace(";", "").replace(")", "");
			//
//	                    String[] terms = expression.split(" ");
			//
//	                    VariableReference leftOperand = null;
//	                    VariableReference rightOperand = null;
			//
//	                    String term0 = terms[0];
			//
//	                    if (term0.matches("true")) {
//	                        leftOperand = test.addStatement(new BooleanPrimitiveStatement(test, true));
//	                    } else if (term0.matches("false")) {
//	                        leftOperand = test.addStatement(new BooleanPrimitiveStatement(test, false));
//	                    } else if (term0.matches("\"")) {
//	                        leftOperand = test.addStatement(new StringPrimitiveStatement(test, term0.replace("\"", "")));
//	                    } else if (term0.matches("[0-9]*")) {
//	                        leftOperand = test.addStatement(new IntPrimitiveStatement(test, Integer.valueOf(term0)));
//	                    } else {
//	                        leftOperand = variableReferences.get(term0);
//	                    }
			//
//	                    String term1 = terms[2];
			//
//	                    if (term1.matches("true")) {
//	                        rightOperand = test.addStatement(new BooleanPrimitiveStatement(test, true));
//	                    } else if (term1.matches("false")) {
//	                        rightOperand = test.addStatement(new BooleanPrimitiveStatement(test, false));
//	                    } else if (term1.matches("\"")) {
//	                        rightOperand = test.addStatement(new StringPrimitiveStatement(test, term0.replace("\"", "")));
//	                    } else if (term1.matches("[0-9]*")) {
//	                        rightOperand = test.addStatement(new IntPrimitiveStatement(test, Integer.valueOf(term1)));
//	                    } else {
//	                        rightOperand = variableReferences.get(term1);
//	                    }
			//
//	                    VariableReference boolean0 = test.addStatement(new BooleanPrimitiveStatement(test));
			//
//	                    PrimitiveExpression primitiveExpression = new PrimitiveExpression(test, boolean0, leftOperand, PrimitiveExpression.Operator.toOperator(terms[1]), rightOperand);
			//
//	                    VariableReference expressionStatment = test.addStatement(primitiveExpression);
			//
//	                    PrimitiveAssertion assertion = new PrimitiveAssertion();
//	                    assertion.setSource(expressionStatment);
//	                    assertion.setValue(true);
			//
//	                    test.getStatement(expressionStatment.getStPosition() + 1).addAssertion(assertion);
////	                } else if (statement.contains("assertNotNull")) {
////	                    System.out.println("Array NotNull " + statement);
////	                    //TODO Assert NotNull
//	                } else if (statement.matches(".*\\..*\\(.*")) {
//	                    System.out.println("Method " + statement);
//	                    String[] tokens = statement.split(" ");
			//
//	                    String identifier = tokens[1];
//	                    String methodName = tokens[3].split("\\.")[1];
//	                    String targetName = tokens[3].split("\\.")[0].trim();
			//
//	                    System.out.println(targetName);
			//
//	                    VariableReference targetReference = variableReferences.get(targetName);
			//
//	                    GenericMethod genericMethod = null;
//	                    ArrayList<VariableReference> references = new ArrayList<>();
			//
//	                    try {
//	                        GenericConstructor objectConstrutor = (new GenericConstructor(Object.class.getConstructor(), Object.class));
//	                        if (methodName.contains("addAsEqual")) {
//	                            VariableReference object = test.addStatement(new ConstructorStatement(test, objectConstrutor, new ArrayList<VariableReference>()));
//	                            references.add(object);
//	                            references.add(object);
//	                            genericMethod = new GenericMethod(loadedClass.getMethod("addAsEqual", Object.class, Object.class), loadedClass);
//	                        } else if (methodName.contains("add")) {
//	                            VariableReference object = test.addStatement(new ConstructorStatement(test, objectConstrutor, new ArrayList<VariableReference>()));
//	                            references.add(object);
//	                            genericMethod = new GenericMethod(loadedClass.getMethod("add", Object.class), loadedClass);
//	                        } else if (methodName.contains("compare")) {
//	                            VariableReference object = test.addStatement(new ConstructorStatement(test, objectConstrutor, new ArrayList<VariableReference>()));
//	                            references.add(object);
//	                            references.add(object);
//	                            genericMethod = new GenericMethod(loadedClass.getMethod("compare", Object.class, Object.class), loadedClass);
//	                        } else if (methodName.contains("getUnknownObjectBehavior")) {
//	                            genericMethod = new GenericMethod(loadedClass.getMethod("getUnknownObjectBehavior"), loadedClass);
//	                        } else if (methodName.contains("isLocked")) {
//	                            genericMethod = new GenericMethod(loadedClass.getMethod("isLocked"), loadedClass);
//	                        } else if (methodName.contains("getClass")) {
//	                            genericMethod = new GenericMethod(loadedClass.getMethod("getClass"), loadedClass);
//	                        }
//	                    } catch (NoSuchMethodException e) {
//	                        e.printStackTrace();
//	                    }
			//
//	                    VariableReference variable = test.addStatement(new MethodStatement(test, genericMethod, targetReference, references));
//	                    variableReferences.put(identifier, variable);
//	                } else if (statement.matches(".* = .*\\..*")) {
//	                    String[] tokens = statement.split(" ");
//	                    String identifier = tokens[1];
//	                    String fieldCall = tokens[3];
			//
//	                    String[] fieldParts = fieldCall.split("\\.");
			//
//	                    String attribute = fieldParts[fieldParts.length - 1];
			//
//	                    GenericField genericField = null;
//	                    GenericConstructor genericConstructor = null;
			//
//	                    try {
//	                        genericField = new GenericField(loadedClass.getField(attribute), loadedClass);
//	                        genericConstructor = new GenericConstructor(loadedClass.getDeclaredConstructor(), loadedClass);
//	                    } catch (NoSuchFieldException e) {
//	                        e.printStackTrace();
//	                    } catch (NoSuchMethodException e) {
//	                        e.printStackTrace();
//	                    }
//	                    System.out.println("Field " + statement);
//	                    VariableReference comparator = test.addStatement(new ConstructorStatement(test, genericConstructor, new ArrayList<VariableReference>()));
			//
//	                    FieldStatement fieldStatement = new FieldStatement(test, genericField, comparator);
			//
//	                    VariableReference variable = test.addStatement(fieldStatement);
//	                    variableReferences.put(identifier, variable);
//	                } else if (statement.contains(" = ")) {
//	                    //TODO Assigment Statment;
//	                    System.out.println("Assigment " + statement);
//	                } else if (statement.contains("assertEquals")) {
//	                    //TODO Assert Equals
//	                } else if (statement.contains("assertTrue")) {
//	                    //TODO Assert
//	                } else {
//	                    System.out.println(statement);
//	                }
//	            }

			test.addStatement(new IntPrimitiveStatement(test, it));

			tests.add(test);
			System.out.println(test.toCode());
		}
		//
//	            switch(it){
//	                case 9:
//	                    try {
//	                        ArrayReference abc  = new ArrayReference(test,new GenericClass(Object[].class),9);
		//
//	                        ArrayStatement aS = new ArrayStatement(test,abc);
		//
//	                        aS.setSize(9);
		//
//	                        VariableReference bvc = test.addStatement(aS);
		//
//	                        GenericConstructor asmda = (new GenericConstructor(Object.class.getConstructor(), Object.class));
		//
//	                        VariableReference asdae = test.addStatement(new ConstructorStatement(test, asmda, new ArrayList<VariableReference>()));
		//
//	                        VariableReference asdae2 = test.addStatement(new ConstructorStatement(test, asmda, new ArrayList<VariableReference>()));
		//
//	                        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("C:\\Users\\Brenno\\Desktop\\TestSmells\\comparators\\target\\classes").toURI().toURL()});
//	                        // Load the class from the classloader by name....
//	                        Class loadedClass = classLoader.loadClass("collections.comparators.FixedOrderComparator");
		//
//	                        GenericConstructor fc = new GenericConstructor( loadedClass.getDeclaredConstructor(Object[].class) , loadedClass);
		//
//	                        ArrayList<VariableReference> references = new ArrayList<>();
		//
//	                        references.add(bvc);
		//
//	                        VariableReference comparator = test.addStatement(new ConstructorStatement(test, fc, references));
		//
//	                        GenericMethod gm = new GenericMethod(loadedClass.getMethod("compare", Object.class, Object.class), loadedClass);
		//
//	                        List<VariableReference> parameters = new ArrayList<>();
		//
//	                        parameters.add(asdae);
		//
//	                        parameters.add(asdae2);
		//
//	                        VariableReference boolean0 = test.addStatement(new MethodStatement(test, gm, comparator, parameters));
		//
//	                        PrimitiveAssertion assertion = new PrimitiveAssertion();
//	                        assertion.setSource(boolean0);
//	                        assertion.setValue(-1);
		//
//	                        test.getStatement(boolean0.getStPosition()).addAssertion(assertion);
		//
//	                    } catch ( Exception e) {
//	                        e.printStackTrace();
//	                    }
//	                    break;
//	                case 10:
//	                    try {
//	                        ArrayReference abc  = new ArrayReference(test,new GenericClass(Object[].class),9);
		//
//	                        ArrayStatement aS = new ArrayStatement(test,abc);
		//
//	                        aS.setSize(9);
		//
//	                        VariableReference bvc = test.addStatement(aS);
		//
//	                        GenericConstructor asmda = (new GenericConstructor(Object.class.getConstructor(), Object.class));
		//
//	                        VariableReference asdae = test.addStatement(new ConstructorStatement(test, asmda, new ArrayList<VariableReference>()));
		//
		//
//	                        VariableReference asdae2 = test.addStatement(new ConstructorStatement(test, asmda, new ArrayList<VariableReference>()));
		//
//	                        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("C:\\Users\\Brenno\\Desktop\\TestSmells\\comparators\\target\\classes").toURI().toURL()});
//	                        // Load the class from the classloader by name....
//	                        Class loadedClass = classLoader.loadClass("collections.comparators.FixedOrderComparator");
		//
//	                        GenericConstructor fc = new GenericConstructor( loadedClass.getDeclaredConstructor(Object[].class) , loadedClass);
		//
//	                        ArrayList<VariableReference> references = new ArrayList<>();
		//
//	                        references.add(bvc);
		//
//	                        VariableReference comparator = test.addStatement(new ConstructorStatement(test, fc, references));
		//
//	                        GenericMethod gm = new GenericMethod(loadedClass.getMethod("compare", Object.class, Object.class), loadedClass);
		//
//	                        List<VariableReference> parameters = new ArrayList<>();
		//
//	                        parameters.add(asdae);
		//
//	                        parameters.add(asdae2);
		//
//	                        VariableReference boolean0 = test.addStatement(new MethodStatement(test, gm, comparator, parameters));
		//
//	                        PrimitiveAssertion assertion = new PrimitiveAssertion();
//	                        assertion.setSource(boolean0);
//	                        assertion.setValue(-1);
		//
//	                        test.getStatement(boolean0.getStPosition()).addAssertion(assertion);
		//
//	                    } catch ( Exception e) {
//	                        e.printStackTrace();
//	                    }
//	                    break;
//	                case 11:
//	                    try {
		//
//	                        ArrayReference abc  = new ArrayReference(test,new GenericClass(Object[].class),3);
		//
//	                        ArrayStatement aS = new ArrayStatement(test,abc);
		//
//	                        aS.setSize(3);
		//
//	                        VariableReference bvc = test.addStatement(aS);
		//
//	                        ArrayIndex ai = new ArrayIndex(test, abc, 0);
		//
//	                        GenericConstructor asmda = (new GenericConstructor(Object.class.getConstructor(), Object.class));
		//
//	                        VariableReference asdae = test.addStatement(new ConstructorStatement(test, asmda, new ArrayList<VariableReference>()));
		//
//	                        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("C:\\Users\\Brenno\\Desktop\\TestSmells\\comparators\\target\\classes").toURI().toURL()});
//	                        // Load the class from the classloader by name....
//	                        Class loadedClass = classLoader.loadClass("collections.comparators.FixedOrderComparator");
		//
//	                        GenericConstructor fc = new GenericConstructor( loadedClass.getDeclaredConstructor(Object[].class) , loadedClass);
		//
//	                        ArrayList<VariableReference> references = new ArrayList<>();
		//
//	                        references.add(bvc);
		//
//	                        VariableReference comparator = test.addStatement(new ConstructorStatement(test, fc, references));
		//
//	                        GenericMethod gm = new GenericMethod(loadedClass.getMethod("compare", Object.class, Object.class), loadedClass);
		//
//	                        List<VariableReference> parameters = new ArrayList<>();
		//
//	                        parameters.add(asdae);
		//
//	                        StringPrimitiveStatement string = new StringPrimitiveStatement(test,"G~kH+HO_opO^_N_N5_R");
		//
//	                        VariableReference stringv = test.addStatement(string);
		//
//	                        parameters.add(stringv);
		//
//	                        VariableReference boolean0 = test.addStatement(new MethodStatement(test, gm, comparator, parameters));
		//
//	                        PrimitiveAssertion assertion = new PrimitiveAssertion();
//	                        assertion.setSource(boolean0);
//	                        assertion.setValue(1);
		//
//	                        test.getStatement(boolean0.getStPosition()).addAssertion(assertion);
		//
//	                    } catch ( Exception e) {
//	                        e.printStackTrace();
//	                    }
//	                    break;
//	                case 18:
//	                    try {
//	                        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("C:\\Users\\Brenno\\Desktop\\TestSmells\\comparators\\target\\classes").toURI().toURL()});
//	                        // Load the class from the classloader by name....
//	                        Class loadedClass = classLoader.loadClass("collections.comparators.FixedOrderComparator");
		//
//	                        ArrayList<VariableReference> references = new ArrayList<>();
		//
//	                        GenericConstructor fc = new GenericConstructor( loadedClass.getDeclaredConstructor() , loadedClass);
		//
//	                        VariableReference comparator = test.addStatement(new ConstructorStatement(test, fc, references));
		//
//	                        GenericMethod gm = new GenericMethod(loadedClass.getMethod("compare", Object.class, Object.class), loadedClass);
		//
//	                        ArrayReference abc  = new ArrayReference(test,new GenericClass(Object[].class),6);
		//
//	                        ArrayStatement aS = new ArrayStatement(test,abc);
		//
//	                        aS.setSize(6);
		//
//	                        VariableReference bvc = test.addStatement(aS);
		//
//	                        ArrayIndex ai = new ArrayIndex(test, abc, 0);
		//
//	                        GenericConstructor asmda = (new GenericConstructor(Object.class.getConstructor(), Object.class));
		//
//	                        VariableReference asdae = test.addStatement(new ConstructorStatement(test, asmda, new ArrayList<VariableReference>()));
		//
//	                        List<VariableReference> parameters = new ArrayList<>();
		//
//	                        parameters.add(ai);
//	                        parameters.add(asdae);
		//
//	                        VariableReference boolean0 = test.addStatement(new MethodStatement(test, gm, comparator, parameters));
		//
//	                        PrimitiveAssertion assertion = new PrimitiveAssertion();
//	                        assertion.setSource(boolean0);
//	                        assertion.setValue(-1);
		//
//	                        test.getStatement(boolean0.getStPosition()).addAssertion(assertion);
		//
//	                    } catch ( Exception e) {
//	                        e.printStackTrace();
//	                    }
//	                    break;
//	                default:
//	                    test.addStatement(new IntPrimitiveStatement(test,it));
//	                    break;
//	            }
		//
//	            tests.add(test);
		//
//	            System.out.println(test.toCode());
//	        }
		//
		System.out.println("SIZE: " + tests.size());

		CoverageGoalTestNameGenerationStrategy naming = new CoverageGoalTestNameGenerationStrategy(tests);

		List<String> testNames = new ArrayList<String>();

		Iterator<TestCase> testsIterator = tests.iterator();

		while (testsIterator.hasNext()) {
			TestCase testCase = testsIterator.next();
			testNames.add(naming.getName(testCase));
			System.out.println(naming.getName(testCase));

			// ArrayList<TestCase> testAux = new ArrayList<>();
			// testAux.add(t);

			// CoverageGoalTestNameGenerationStrategy namings = new
			// CoverageGoalTestNameGenerationStrategy(testAux);

			// System.out.println(namings.getName(t));
//	            List<TestFitnessFunction> sortedList = new ArrayList<>(t.getCoveredGoals());
//	            Collections.sort(sortedList);
			//
//	            for(TestFitnessFunction goal : sortedList ){
//	                System.out.println(goal);
//	            }
		}

		Iterator<String> testNamesIterator = testNames.iterator();

		MethodDeclaration[] methodDeclarations = typeDecl.getMethods();
		
		for (int i = 0; i < methodDeclarations.length ; i++) {
		//for (int i = methodDeclarations.length - 1; i >= 0; i--) {
			MethodDeclaration methodDeclaration = methodDeclarations[i];

			String newTestName = testNamesIterator.next();

//			if (newTestName.matches(".*[1-9][0-9]*") && !newTestName.matches("test[0-9][0-9]*")) {
//				rewriter.remove(methodDeclaration, null);
//			} else {
				rewriter.replace(methodDeclaration.getName(), astRoot.getAST().newSimpleName(newTestName), null);
			//}

		}

		try {
			TextEdit edits = rewriter.rewriteAST();
			// apply the text edits to the compilation unit
			Document document = new Document(unit.getSource());

			edits.apply(document);

			// this is the code for adding statements
			unit.getBuffer().setContents(document.get());

			unit.applyTextEdit(edits, new NullProgressMonitor());
		} catch (JavaModelException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "Plugin", "Exemplo");
		return null;
	}

	private Type stringType() {
		return Type.getType("Ljava.lang.String;");
	}

	private Type objectType() {
		return Type.getType("Ljava.lang.Object;");
	}

	private Set[] readCoveredGoalsFile(String filePath, int suiteSize) throws IOException {
		Set[] allCoveredGoals = new HashSet[suiteSize];

		BufferedReader buffRead = new BufferedReader(new FileReader(filePath));
		String linha = "";

		Set<String> testCoveredGoals = new HashSet<>();
		int testCase = 0;

		System.out.println(buffRead.readLine());
		// buffRead.readLine();
		linha = buffRead.readLine();

		while (testCase < suiteSize) {
			if (linha != null) {
				if (linha.contains("[")) {
					testCoveredGoals.add(linha);
					// System.out.println(linha);
				} else {
					System.out.println(linha);
					allCoveredGoals[testCase] = testCoveredGoals;
					testCoveredGoals = new HashSet<>();
					testCase++;
				}
			} else {
				allCoveredGoals[testCase] = testCoveredGoals;
				;
				break;
			}
			linha = buffRead.readLine();
		}
		buffRead.close();

		return allCoveredGoals;
	}

	private HashMap<Integer, String>[] readStatementFile(String filePath, int suiteSize) throws IOException {
		// ArrayList[] alltestsStatements = new ArrayList[suiteSize];

		HashMap<Integer, String>[] statementsMap = new HashMap[suiteSize];

		BufferedReader buffRead = new BufferedReader(new FileReader(filePath));
		String line = "";

		HashMap<Integer, String> testStatements = new HashMap<>();
//        ArrayList<String> testStatements = new ArrayList<>();
		int testCase = 0;

		buffRead.readLine();
		line = buffRead.readLine();

		while (testCase < suiteSize) {
			if (line != null) {
				if (!line.equals("")) {
					int statementNumber = Integer.valueOf(line.split(" ", 2)[0]);
					String statement = line.split(" ", 2)[1];

					if (!line.contains("public void test") && !line.equals("")) {
						testStatements.put(statementNumber, statement);
					} else {
						statementsMap[testCase] = testStatements;
						testStatements = new HashMap<>();
						testCase++;
					}
				}
			} else {
				statementsMap[testCase] = testStatements;
				break;
			}
			line = buffRead.readLine();
		}
		buffRead.close();

		return statementsMap;
	}

	private HashMap<Integer, String> readMethodCallsFile(String filePath) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(filePath));
		String line = "";

		HashMap<Integer, String> methodCalls = new HashMap<>();

		//buffRead.readLine();
		line = buffRead.readLine();

		while (line != null) {
			int lineNumber = Integer.valueOf(line.split(" ", 2)[0]);
			String methodCall = line.split(" ", 2)[1];
			methodCalls.put(lineNumber, methodCall);
			line = buffRead.readLine();
		}
		buffRead.close();

		return methodCalls;
	}

	private void loadClasses(String classesPath, String packageName, String packageDirectory) {
		if (classesPath != null) {
			System.out.println("PACKAGE DIRECTORY: " + packageDirectory);
			File f = new File(packageDirectory);
			String[] list = f.list();
			for (String c : list) {
				if (c.endsWith(".class")) {
					try {
						URLClassLoader classLoader = new URLClassLoader(
								new URL[] { new File(classesPath).toURI().toURL() });
						String className = packageName + c.replace(".class", "");
						System.out.println("CLASS PATH: " + classesPath);
						System.out.println("CLASS NAME: " + className);
						Class.forName(className, true, classLoader);
					} catch (ClassNotFoundException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					} catch (MalformedURLException e) {
						System.out.println("ERROR");
						e.printStackTrace();
					}
				} else {
					if (new File(packageDirectory + "\\" + c).isDirectory())
						packageDirectory = packageDirectory + "\\" + c;
					loadClasses(classesPath, packageName + c + ".", packageDirectory);
				}
			}
		}
	}

	private Class loadClass(String classesPath, String className) throws IOException, ClassNotFoundException {
		Class loadedClass;
		switch (className) {
		case "boolean":
		case "Boolean":
		case "java.lang.Boolean":
			loadedClass = boolean.class;
			break;
		case "int":
		case "Integer":
		case "java.lang.Integer":
			loadedClass = int.class;
			break;
		case "byte":
		case "Byte":
		case "java.lang.Byte":
			loadedClass = byte.class;
			break;
		case "short":
		case "Short":
		case "java.lang.Short":
			loadedClass = short.class;
			break;
		case "long":
		case "Long":
		case "java.lang.Long":
			loadedClass = long.class;
			break;
		case "float":
		case "Float":
		case "java.lang.Float":
			loadedClass = float.class;
			break;
		case "double":
		case "Double":
		case "java.lang.Double":
			loadedClass = double.class;
			break;
		case "char":
		case "Char":
			loadedClass = char.class;
			break;
		default:
			//if(className.contains("/")){
			//	className = className.replace("/", ".");
			//}
			URLClassLoader classLoader = new URLClassLoader(new URL[] { new File(classesPath).toURI().toURL() },
					Module.class.getClassLoader());
			try {
				loadedClass = Class.forName(className, true, classLoader);
				classLoader.close();
			} catch (ClassNotFoundException e) {
				loadedClass = Class.forName(className);
			}
			
			break;
		}
		return loadedClass;
	}
}
