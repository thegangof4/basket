package codingtest.basket;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A console driven program to enter a Basket of items, and to compute the total cost of the basket
 */
public class BasketTestConsole {

    private static final Scanner in = new Scanner(System.in);
    private static final PrintStream out = new PrintStream(System.out);

    public BasketTestConsole() {
	}
	
    /**
     * Returns a new basket of items, where the number and cost of each item are provide by command line queries
     * @return the new basket of items
     */
	public Basket getBasket() {
		Basket basket = new Basket();
		for (BasketItem item:BasketItem.values()) {
			enterCost(item);
			enterQty(item, basket);
 		}		
		return basket;
	}

	private void enterCost(BasketItem item) {
		float itemCost = 0.0f;
		while (itemCost == 0.0f) {
			try {
				out.print("Enter the cost of " + item + ": ");
				itemCost = in.nextFloat();
				item.setCost(itemCost);
			} catch (InputMismatchException nfe) {
				out.println("Error in reading item cost");
				in.nextLine();
			}
		}
	}

	private void enterQty(BasketItem item, Basket basket) {
		int itemCount = 0;
		while (itemCount == 0) {
			try {
				out.print("Enter the number of " + item + " in the basket: ");
				itemCount = in.nextInt();
				basket.addItemCount(item, itemCount);
			} catch (InputMismatchException nfe) {
				out.println("Error in reading item count");
				in.nextLine();
			}
		}
	}

	public static void main(String[] args) {
		BasketTestConsole test = new BasketTestConsole();
		Basket basket = test.getBasket();
		System.out.println(basket);
		System.out.println(String.format("Total basket cost: $%5.2f	", basket.totalCost()));
	}
}
