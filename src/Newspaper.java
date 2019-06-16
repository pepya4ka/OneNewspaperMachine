public enum Newspaper {

    PROMOTIONAL_AND_INFORMATIONAL("Рекламно-информационная", 10, 1),
    ENTERTAINING("Развлекательная", 20, 2),
    DAILY("Ежедневная", 30, 3),
    WEEKLY("Еженедельная", 40, 4);

    private String title;
    private int price;
    private int number;

    Newspaper(String title, int price, int number) {
        this.title = title;
        this.price = price;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getNumber() {
        return number;
    }
}