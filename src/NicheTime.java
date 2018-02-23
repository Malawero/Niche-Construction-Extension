/*
	Extension. Consisting of the next classes
	- NicheTime
	- Population
	- Genotype
	- Model
	
	I declare that all code was written by myself
 */
import java.util.ArrayList;
import java.util.Collections;

public class NicheTime {
	public Population total;
	
	public ArrayList<Population> groups;
	
	public double[] frequencies;
	
	public static void main(String[] args) {
		NicheTime nicheTime = new NicheTime();
		
		nicheTime.evolution();
	}
	
	public NicheTime() {
		double[] freqs = new double[Genotype.genotypes.length];
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			freqs[i] = (double) Model.N / Genotype.genotypes.length;
		}
		
		total  = new Population(freqs);
		
		groups = new ArrayList<Population>();
	}
	
	public void evolution() {
		double generation = 1;
		
		boolean dispersal = true;
		
		while(generation <= Model.T) {
			if(dispersal) {
				this.aggregation(this.total);
				this.print(0);
			}
			
			this.reproduction();
			
			this.frequencies = new double[Genotype.genotypes.length];
			dispersal = false;
			
			if(generation % Model.SHORT == 0) {
//				this.print(1);
				this.dispersal(Genotype.SHORT);
				dispersal = true;
			}
			
			if(generation % Model.LONG == 0) {
//				this.print(2);
				this.dispersal(Genotype.LONG);
				dispersal = true;
			}
			
			if(this.groups.isEmpty()) {
				this.rescale();
			}
			
			if(dispersal)
				this.total  = new Population(this.frequencies);
			
			generation++;
		}//while
	}//evolution
	
	public void aggregation(Population pop) {
		ArrayList<Genotype> small = new ArrayList<Genotype>();
		ArrayList<Genotype> large = new ArrayList<Genotype>();
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			if(pop.genotypes[i].groupSize == Genotype.SMALL)
				small.addAll(pop.genotypes[i].getIndividuals());
			
			if(pop.genotypes[i].groupSize == Genotype.LARGE)
				large.addAll(pop.genotypes[i].getIndividuals());
		}
		
		Collections.shuffle(small);
		
		while( small.size() >= Model.SMALL) {
			groups.add(new Population(small, Model.SMALL, Model.RS));
		}
		
		Collections.shuffle(large);
		
		while( large.size() >= Model.LARGE) {
			groups.add(new Population(large, Model.LARGE, Model.RL));
		}
	}//aggregation
	
	public void reproduction() {
		for(Population pop : this.groups) {
			pop.reproduction();
		}
	}
	
	public void dispersal(char time) {		
		ArrayList<Population> groups = new ArrayList<Population>();
		
		while( this.groups.size() > 0 ){
			Population pop = this.groups.remove(0);
			
			boolean empty = true;
			
			for(int i = 0; i < Genotype.genotypes.length; i++) {
				if(pop.genotypes[i].time == time) {
					this.frequencies[i] = this.frequencies[i] + pop.genotypes[i].frequency;
					pop.genotypes[i].frequency = 0;
				}
				if(pop.genotypes[i].frequency > 0)
					empty = false;
			}//for each Genotype
			
			if(empty) {
				pop = null;
			}else {
				groups.add(pop);
			}
		}
		this.groups = null;
		this.groups = groups;
	}
	
	public void rescale() {
		double sum = 0;
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			sum = sum + this.frequencies[i];
		}
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			this.frequencies[i] = (Model.N * this.frequencies[i])/sum;
		}
	}
	
	public void print(int event) {
		double[] freqs = new double[Genotype.genotypes.length];
		
		for(Population pop : this.groups) {
			for(int i = 0; i < Genotype.genotypes.length; i++) {
				freqs[i] = freqs[i] + pop.genotypes[i].frequency;
			}
		}
		
		double sum = 0;
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			sum = sum + freqs[i];
		}
		
		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append(event+",");
		
		for(int i = 0; i < Genotype.genotypes.length; i++) {
			freqs[i] = freqs[i] / sum;
			stringBuilder.append(freqs[i]+",");
		}
		System.out.println(stringBuilder.toString());
	}
	
	public void printGroups() {
		for(Population pop : this.groups) {
			pop.print();
		}
	}//printGroups
}
