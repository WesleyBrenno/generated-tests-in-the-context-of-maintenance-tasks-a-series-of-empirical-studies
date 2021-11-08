package visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class VariableDeclarationStatementVisitor extends ASTVisitor {
    private List<VariableDeclarationStatement> variableDeclarationStatements = new ArrayList<>();
    private String variableName;
    private int limitPosition;
    
    public VariableDeclarationStatementVisitor(String name, int limit) {
    	super();
    	variableName = name;
    	limitPosition = limit;
    }

    @Override
    public boolean visit(VariableDeclarationStatement node) {
    	
    	
		SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor();
    	
    	node.accept(simpleNameVisitor);
    	
    	List<SimpleName> simpleNames = simpleNameVisitor.getNames();
    	
    	Iterator<SimpleName> namesIt = simpleNames.iterator();
    	
    	while(namesIt.hasNext()) {
    		if(namesIt.next().toString().equals(variableName) && !(node.getParent().getParent() instanceof TryStatement) && node.getStartPosition() < limitPosition) {
    			variableDeclarationStatements.add(node);
    			break;
    		}
    	}

//      VariableDeclarationFragmentVisitor fragmentVisitor = new VariableDeclarationFragmentVisitor();    	
//    	node.accept(fragmentVisitor);
//    	
//    	Iterator<VariableDeclarationFragment> fragmentsIt = fragmentVisitor.getDeclarationFragments().iterator();
//    	
//    	while(fragmentsIt.hasNext()) {
//    		if(fragmentsIt.next().getName().toString().equals(variableName) && !(node.getParent().getParent() instanceof TryStatement)) {
//    			variableDeclarationStatements.add(node);
//    			break;
//    		}
//    	}
    	
    	return super.visit(node);
    }

    public List<VariableDeclarationStatement> getDeclarations() {
        return variableDeclarationStatements;
    }
}