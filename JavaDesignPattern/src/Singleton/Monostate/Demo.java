package Singleton.Monostate;

class ChiefExecutiveOffice {

    private static String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOffice.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOffice.age = age;
    }

    @Override
    public String toString() {
        return "ChiefExecutiveOffice{ " +
        "name='" + name + '\'' + 
        ", age=" + age + '\'' + '}';
    }
}

public class Demo {
    public static void main(String[] args) {
        ChiefExecutiveOffice ceo = new ChiefExecutiveOffice();

        ceo.setAge(55);
        ceo.setName("Adam");

        ChiefExecutiveOffice ceo2 = new ChiefExecutiveOffice();
        System.out.println(ceo2);
    }
}
