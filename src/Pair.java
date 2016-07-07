public class Pair {
   int a;
   int b;
   public Pair(int a, int b) {
      this.a = a;
      this.b = b;
   }
	@Override
	public boolean equals(Object other){
    if (other == null) 
	 	return false;
    if (other == this) 
	 	return true;
    if (!(other instanceof Pair))
	 	return false;
    Pair otherMyClass = (Pair) other;
    return a==otherMyClass.a && b==otherMyClass.b;
	}
	public int hashCode() {
		return 17*a+31*b;
	}
}