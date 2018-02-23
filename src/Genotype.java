import java.util.ArrayList;

public class Genotype {
	public static final String[] genotypes = {"CSS", "CLS", "CSL", "CLL", "SSS", "SLS", "SSL", "SLL"};

	public static final char COOP   = 'C';
	public static final char SELF   = 'S';
	public static final char SMALL  = 'S';
	public static final char LARGE  = 'L';
	public static final char SHORT  = 'S';
	public static final char LONG   = 'L';
	
//	public static final int  SMALL = 4;
//	public static final int  LARGE = 40;
	
	public char strategy;
	public char groupSize;
	public char time;
	public String value;
	
	public double frequency;
	public double share;
	public double growth;
	public double consume;
	
	public void initialise(char strategy, char size, char time) {
		this.strategy  = strategy;
		this.groupSize = size;
		this.time      = time;
		
		this.frequency = 0;
		
		if(this.strategy == Genotype.COOP) {
			this.growth  = Model.GC;
			this.consume = Model.CC;
		}else {
			this.growth  = Model.GS;
			this.consume = Model.CS;
		}
	}
	
	public Genotype(Genotype original){
		this.initialise(original.strategy, original.groupSize, original.time);
		this.value = original.value;
	}
	
	public Genotype(String genotype){
		this.initialise(genotype.charAt(0), genotype.charAt(1), genotype.charAt(2));
		this.value = genotype;
	}
	
	public Genotype(String genotype, double freq){
		this.initialise(genotype.charAt(0), genotype.charAt(1), genotype.charAt(2));
		this.frequency = freq;
		this.value = genotype;
	}
	
	public int classify() {
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			if(this.value.equals(Genotype.genotypes[i]))return i;
		}
		return Genotype.genotypes.length;
	}
	
	public double share() {
		this.share = this.frequency * this.growth * this.consume;
		return this.share;
	}
	
	public void reproduce(){
		this.frequency = this.frequency + (this.share/this.consume) - (Model.K * this.frequency);
		this.frequency = Math.rint(this.frequency);
	}
	
	public ArrayList<Genotype> getIndividuals() {
		ArrayList<Genotype> sub = new ArrayList<Genotype>();
		
		double size = Math.rint(this.frequency);
		
		for(double i = 0; i <= size; i++) {
			sub.add(new Genotype(this));
		}
		
		return sub;
	}//sub
	
	public double getFreq() {
		return this.frequency/Model.N;
	}
	
//	public int classify() {
//	StringBuilder stringBuilder = new StringBuilder();
//	
//	stringBuilder.append(this.strategy);
//	stringBuilder.append(this.groupSize);
//	stringBuilder.append(this.time);
//	
//	String string = stringBuilder.toString();
//	
//	for(int i = 0; i < Genotype.genotypes.length; i++) {
//		if(string.equals(Genotype.genotypes[i]))return i;
//	}
//	return Genotype.genotypes.length;
//}

//public Genotype(char strategy, char size, char time) {
//	this.initialise(strategy, size, time);
//}
//
//public Genotype(char strategy, char size, char time, double freq) {
//	this.initialise(strategy, size, time);
//	this.frequency = freq;
//}
}
