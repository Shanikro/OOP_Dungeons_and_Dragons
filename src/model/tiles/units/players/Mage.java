package model.tiles.units.players;

public class Mage  extends Player{

    private final int MAGE_ADDITIONAL_MANA_CAP = 25;
    private final int WARRIOR_ADDITIONAL_SPELL_POWER = 10;
    private final int WARRIOR_ADDITIONAL_DEFENSE = 1;

    private int manaCap;
    private int manaCurr;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;


    public Mage(String name, int hitPoints, int attack, int defense, int manaCap, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, hitPoints, attack, defense);
        this.manaCap = manaCap;
        this.manaCurr = manaCap/4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this. hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    public void levelUp(){
        super.levelUp();
        this.manaCap += MAGE_ADDITIONAL_MANA_CAP * this.level;
        this.manaCurr = Math.min( this.manaCurr + (manaCap/4), manaCap);
        this.spellPower += WARRIOR_ADDITIONAL_SPELL_POWER * this.level;
    }

    //game tick
    public void gameTick(){
        this.manaCurr = Math.min( this.manaCap, this.manaCurr + this.level);
    }

    //use special ability
    public void useSA(){
        this.manaCurr -= this.manaCost ;
        int hits = 0;
       /* while (hits< hitsCount && ) {

        }*/

    }
}
