import java.util.ArrayList;

public class Population {
	public double R;
	
	public Genotype[] genotypes;
	
	public Population(double[] freqs) {
		this.genotypes = new Genotype[Genotype.genotypes.length];
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			this.genotypes[i] = new Genotype(Genotype.genotypes[i], freqs[i]);
		}
	}//Population(freqs)
		
	public Population(ArrayList<Genotype> genotypes, int size, double resource) {
		this.genotypes = new Genotype[Genotype.genotypes.length];
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			this.genotypes[i] = new Genotype(Genotype.genotypes[i]);
		}
		
		this.R = resource;
		
		for(int i = 0; i < size; i++) {
			Genotype genotype = genotypes.remove(0);
			this.genotypes[genotype.classify()].frequency++;
		}
	}//Population(individuals, size)
	
	public void reproduction(int timeSteps) {
		for(int i = 0; i < timeSteps; i++) {
			this.reproduction();
		}
	}//reproduction(t)
	
	public void reproduction() {
		double sum = 0;
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			sum = sum + this.genotypes[i].share();
		}
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			this.genotypes[i].share = (this.genotypes[i].share / sum) * this.R;
		}
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			this.genotypes[i].reproduce();
		}
	}//reproduction()
	
	public void print() {
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			stringBuilder.append(this.genotypes[i].getFreq()+",");
		}
		System.out.println(stringBuilder.toString());
	}
}
