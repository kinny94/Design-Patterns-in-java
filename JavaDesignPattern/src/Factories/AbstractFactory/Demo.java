package Factories.AbstractFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.lang.reflect.Type;
import java.util.AbstractMap.SimpleEntry;
import org.reflections.Reflections;

// Abstract Factory is a pattern which makes a correspondence between the heirarchy of objects that you have.

interface HotDrink {
    void consume();
}

class Tea implements HotDrink {

    @Override
    public void consume() {
        System.out.println("This tea is delicious");
    }
}

class Coffee implements HotDrink {

    @Override
    public void consume() {
        System.out.println("This coffee is delicious");        
    }
}

interface HotDrinkFactory {
    HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory {

    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Put in tea bag, boil water, pour " + amount + "ml, add lemon, enjoy!"
        );

        return new Tea();
    }
}

class CoffeeFactory implements HotDrinkFactory {

    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Grind some beans, boil water, pour " + amount + "ml, add cream and sugar, enjoy!");
        return new Coffee();
    }
}

class HotDrinkMachine {
    private List<SimpleEntry<String, HotDrinkFactory>> namedFactories = new ArrayList<>();

    public HotDrinkMachine() throws Exception {
        Set<Class<? extends HotDrinkFactory>> types = new Reflections("").getSubTypesOf(HotDrinkFactory.class);

        for (Class <? extends HotDrinkFactory> type: types) {
            namedFactories.add(
                new SimpleEntry<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()   
                )
            );
        }
    }

    public HotDrink makeDrink() throws Exception {
        System.out.println("Available drinks: ");
        for (int index = 0; index < namedFactories.size(); ++index) {
            SimpleEntry<String, HotDrinkFactory> item = namedFactories.get(index);
            System.out.println("" + index + ": " + item.getKey());
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String s;
            int i, amount;
            if (
                (s = bufferedReader.readLine()) != null 
                && (i = Integer.parseInt(s)) > 0 
                && i < namedFactories.size()
            ) {
                System.out.println("Specify amount: ");
                s = bufferedReader.readLine();
                if (s != null && (amount = Integer.parseInt(s)) > 0) {
                    return namedFactories.get(i).getValue().prepare(amount);
                }
            }

            System.out.println("Incorrect input, try again!");
        }
    }
}

public class Demo {
    public static void main(String[] args) throws Exception {
        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink = machine.makeDrink();
        drink.consume();
    }
}
