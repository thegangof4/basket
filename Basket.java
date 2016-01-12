package codingtest.basket;

import java.util.HashMap;
import java.util.Map;

/**
 * A collection of basket items, with a given number of each item in the basket
 */
public class Basket {
	private Map<BasketItem, Integer>basketMap = new HashMap<BasketItem, Integer>();

	public Basket() {
		super();
	}

	/**
	 * Adds a specific number of basket items to the basket
	 * @param type the type of basket item added to the basket
	 * @param quantity the number of items of the type added to the basket
	 */
	public void addItemCount(BasketItem type, int quantity) {
		int currQuantity = (basketMap.get(type) == null ? 0 : basketMap.get(type).intValue());
		currQuantity += quantity;
		basketMap.put(type, new Integer(currQuantity));
	}
	
	/**
	 * Sets a specific number of basket items to the basket
	 * @param type the type of basket item added to the basket
	 * @param quantity the number of items of the type in the basket
	 */
	public void setItemCount(BasketItem type, int quantity) {
		basketMap.put(type, new Integer(quantity));
	}
	
	/**
	 * The number of items of a given type in the basket
	 * @param type the type of basket item
	 * @return the number of items of the type in the basket
	 */
	public int getItemCount(BasketItem type) {
		return (basketMap.get(type) == null ? 0 : basketMap.get(type).intValue());
	}
	
	/**
	 * The total cost of all the items in the basket
	 * @return the total cost of the basket
	 */
	public float totalCost() {
		float totalCost = 0.0f;	
		for (BasketItem item:basketMap.keySet()) {
			totalCost += item.getCost() * basketMap.get(item).floatValue();
		}
		return totalCost;
	}
	
	/**
	 * A description of the contents of the basket
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("\nBasket Contents\n");
		for (BasketItem item:basketMap.keySet()) {
			sb.append(basketMap.get(item).intValue()).append(' ').append(item).append(" @ $").append(String.format("%5.2f", item.getCost())).append('\n');
		}
		return sb.toString();
	}
}
