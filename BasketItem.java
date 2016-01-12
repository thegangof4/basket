package codingtest.basket;

public enum BasketItem {
	Bananas, Oranges, Apples, Lemons, Peaches;

	private float cost = 0.0f;

	BasketItem() { 
	}
	
	/**
	 * The unit cost of the item
	 * @return the unit cost
	 */
	public float getCost() {
		return cost;
	}

	/**
	 * Sets the unit cost of the item
	 * @param cost the unit cost
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}
}
