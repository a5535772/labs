package com.example.demo.pm.kano.pojo;

public class Story {
	private String name;

	private double si;
	private double dsi;

	public Story(String name, double si, double dsi) {
		super();
		this.name = name;
		this.si = si;
		this.dsi = dsi;
	}

	public String getName() {
		return name;
	}

	public double getSi() {
		return si;
	}

	public double getDsi() {
		return dsi;
	}

	@Override
	public String toString() {
		return "Story [name=" + name + ", si=" + si + ", dsi=" + dsi + "]";
	}

}
