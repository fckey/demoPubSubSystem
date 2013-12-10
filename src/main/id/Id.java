package main.id;

public abstract class Id {
	protected String id;
	
	public String getId() {
		return id;
	}
	
	abstract public boolean equals(Id targetId);
	abstract public int compareTo(Id targetId);
	
}
