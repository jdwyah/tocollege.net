package com.apress.progwt.server.gwt;

class CharVector {
	private int capacityIncrement;
	private char chars[];
	private int size;

	public CharVector(int initialCapacity, int capacityIncrement) {
		assert (initialCapacity >= 0);
		assert (capacityIncrement >= 0);

		this.capacityIncrement = capacityIncrement;
		chars = new char[initialCapacity];
	}

	public void add(char ch) {
		if (size >= chars.length) {
			int growBy = (capacityIncrement == 0) ? chars.length * 2 : capacityIncrement;
			char newChars[] = new char[(chars.length + growBy)];
			System.arraycopy(chars, 0, newChars, 0, size);
			chars = newChars;
		}

		chars[size++] = ch;
	}

	public char[] asArray() {
		return chars;
	}

	public char get(int index) {
		assert (index < size);

		return chars[index];
	}

	public int getSize() {
		return size;
	}

	public void set(int index, char ch) {
		assert (index >= 0 && index < size);

		chars[index] = ch;
		++size;
	}
}