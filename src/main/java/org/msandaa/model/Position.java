package org.msandaa.model;

import java.util.Objects;


public class Position {

	 public final String name;
	  public final double x;
	  public final double y;
	  
	  public Position(String name, double x, double y) {
	    this.name = Objects.requireNonNull(name);
	    this.x = x;
	    this.y = y;
	  }
	  
	  @Override
	  public String toString() {
	    return (name + ": x=" + x + " y=" + y);
	  }
	  
	  @Override
	  public int hashCode() {
	    return Objects.hash(name, x, y);
	  }
	  
	  @Override
	  public boolean equals(Object o) {
	    if (o == this) {
	      return true;
	    }
	    if (!(o instanceof Position)) {
	      return false;
	    }
	    Position that = (Position) o;
	    return name.equals(that.name) && x == that.x && y == that.y;
	  }
	
}
