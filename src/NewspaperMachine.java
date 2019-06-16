public class NewspaperMachine {
    private int money;

    NewspaperMachine() {
        money = 100;
    }

    NewspaperMachine(int money) {
        this.money = money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}