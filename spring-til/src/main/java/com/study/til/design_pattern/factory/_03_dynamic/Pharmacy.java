package com.study.til.design_pattern.factory._03_dynamic;

import com.study.til.design_pattern.factory.Customer;
import com.study.til.design_pattern.factory.Medicine;
import com.study.til.design_pattern.factory._02_after.MedicineFactory;

import java.util.Scanner;

public class Pharmacy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MedicineFactory factory = new MedicineFactory();
        String input = "";

        while (true) {
            input = scanner.nextLine();

            if (input.equals("-1")) {
                break;
            }

            Customer customer = Customer.create(input);
            Medicine medicine = factory.getMedicine(customer);
            medicine.get();
        }
    }
}
