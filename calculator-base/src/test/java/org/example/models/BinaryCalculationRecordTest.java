package org.example.models;

import org.example.enums.BinaryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryCalculationRecordTest {

    @Test
    void formatShouldReturnCorrectString() {
        BinaryCalculationRecord record =
                new BinaryCalculationRecord(BinaryType.ADD, 2, 3, 5);

        String result = record.format();

        assertEquals("2.0 ADD 3.0 = 5.0", result);
    }

    @Test
    void formatShouldPreserveDecimalValues() {
        BinaryCalculationRecord record =
                new BinaryCalculationRecord(BinaryType.MULTIPLY, 2.5, 4.0, 10.0);

        assertEquals("2.5 MULTIPLY 4.0 = 10.0", record.format());
    }
}