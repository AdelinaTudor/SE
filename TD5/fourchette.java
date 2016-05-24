import java.util.Arrays;

/** liste fourchette que doivent se partager les philosophes*/
public class fourchettes {
	/** tableau d'occupation des fourchettes false = occupee, true = libre*/
     boolean[] fourchettes;
 
     int taille;
    
    /** constructeur initialisant la taille et le tableau des fourchettes a true*/
    public fourchettes(int _taille) {
    	fourchettes = new boolean[_taille];
    	Arrays.fill(fourchettes, true);
        taille = _taille;
    }
    
    /** fonction appelee par un processus philosophe no. <br>
     * Si la fourchette de gauche (no) et de droite (no+1) est libre alors le philosophe les prend, 
     * sinon, il est mis en attente
     * @param no no du philosophe demandant les fourchettes*/
    public synchronized void prendre(int no) {
    	int gauche = no;
    	int droite = (no+1)%taille;    	
        while (!fourchettes[gauche] || !fourchettes[droite]) {            
            try   {  wait();  }
            catch (InterruptedException e) {}
        }
        fourchettes[gauche] = false;
        fourchettes[droite] = false;
    }
    
    /** fonction appelee par un processus philosophe no. <br>
     * libere la fourchette de gauche (no) et de droite (no+1) <br> 
     * et reveille les processus en attente sur les fourchettes
    * @param no no du philosophe deposant les fourchettes*/
    public synchronized void deposer(int no) {
    	int gauche = no;
    	int droite = (no+1)%taille;    	
        fourchettes[gauche] = true;
        fourchettes[droite] = true;
        notifyAll(); // reveille les processus en attente de fourchettes
    }       
    
    /** retourne sous forme de chaine l'etat des differentes fourchettes*/
    public String toString() {
        String chaine = "";
        for(int i=0; i<taille; i++)
            chaine = chaine + fourchettes[i] + " ; ";
        return chaine;
    }


}
