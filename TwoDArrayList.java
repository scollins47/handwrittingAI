

import java.util.ArrayList;

public class TwoDArrayList {
	private ArrayList<ArrayList<Double>> list;

	public TwoDArrayList() {
		list = new ArrayList<ArrayList<Double>>();
		list.add(new ArrayList<Double>());
	}

	public void addList()// adds an arraylist to the main arrayList
	{
		list.add(new ArrayList<Double>());
	}

	public boolean add(Double e)// adds an element to the first array list at the first available index
	{
		list.get(0).add(e);
		return true;
	}

	public boolean add(Double e, int whichList)// adds an element to the desired arraylist and adds the element to the
												// first available spot
	{
		list.get(whichList).add(e);
		return true;
	}

	public boolean add(Double e, int whichList, int index)// adds an element to the desired index int the desired
															// arraylist returns true if complete
	{
		list.get(whichList).add(index, e);
		return true;
	}

	public ArrayList<Double> returnList(int index)// returns the ArrayList at the given index
	{
		return list.get(index);
	}

	public Double getAt(int array, int index)// gets the element at the desired arraylist at the desired index
	{
		return list.get(array).get(index);
	}

	public int size()// returns the size of the main arrayList
	{
		return list.size();
	}

	public int sizeAt(int index)// return the size of the array at index
	{
		return list.get(index).size();
	}

	public ArrayList<Double> removeList(int index)// removes and returns the list at the index from the main list
	{
		return list.remove(index);
	}

	public Double removeAt(int array, int index)// removes an element from the inner arrayList at array, in that array
												// removes an element at index
	{
		return list.get(array).remove(index);
	}

	public boolean isEmpty()// returns true if the main array is empty
	{
		return list.size() == 0;
	}

	public boolean isEmpty(int index)// returns true if the arrayList at a certain index is empty
	{
		return list.get(index).size() == 0;
	}

	@Override
	public String toString()// returns the contents of the array
	{
		String temp = "";
		for (int r = 0; r < list.size(); r++) {
			for (int c = 0; c < list.get(r).size(); c++) {
				temp += list.get(r).get(c) + " ";
			}
			temp += "\n";
		}
		return temp;
	}
}