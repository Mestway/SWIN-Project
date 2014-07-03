package polyglot.ext.update.parse;

import java.util.List;

import polyglot.util.Position;

/**
 * Encapsulates some of the data in a constructor declaration.  Used only by the parser.
 */
public class ConstructorDeclarator {
	public Position pos;
	public String name;
	public List formals;

	public ConstructorDeclarator(Position pos, String name, List formals) {
		this.pos = pos;
		this.name = name;
		this.formals = formals;
	}
	
	public Position position() {
		return pos;
	}

    public String name(){
        return name;
    }

    public List formals(){
        return formals;
    }
}
