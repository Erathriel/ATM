package fr.ufc.l3info.oprog;

/**
 * Created by aaaa25 on 26/10/2017.
 */
public class MyCard implements Card {
    private int pin;
    private Account acc;
    private boolean isCustom;
    private boolean isBlocked;
    private int nbAuthentification;

    public MyCard() {
        this.pin = 0;
        this.acc = null;
        this.isCustom = false;
        this.isBlocked = true;
        this.nbAuthentification = 0;

    }

    public boolean setAccount(Account a) {
        if (this.isCustom) {
            return false;
        }
        else {
            if (a != null) {
                this.acc = a;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean setPin(int p) {
        if (this.isCustom) {
            return false;
        }
        else {
            String p2 = Integer.toString(p);
            if (p2.length()==4) {
                this.pin = p;
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean endPersonalization() {
        if (this.pin != 0 && this.acc != null) {
            this.setCustom(true);
            this.isBlocked = false;
            return true;
        }
        else {
            this.setCustom(false);
            return false;
        }
    }

    public Account getAccount() {
        if (this.acc != null) {
            return this.acc;
        }
        else {
            return null;
        }
    }

    public boolean isBlocked() {
        if (this.isBlocked) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setCustom(boolean b) {
        this.isCustom = b;
        return isCustom;
    }

    public boolean checkPin(int p) {
        if (this.pin == p && endPersonalization() && !this.isBlocked) {
            return  true;
        }
        else if(this.pin != p && endPersonalization() && !this.isBlocked) {
            nbAuthentification++;
            if (nbAuthentification == 3) {
                this.isBlocked=true;
            }
            return false;
        }
        else {
            return false;
        }
    }

}
