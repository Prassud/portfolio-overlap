package com.fabric.portfolio;

import com.fabric.portfolio.io.ConsoleOutputHandler;
import com.fabric.portfolio.io.FileInputHandler;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PortfolioOverlapAppSITest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldStartExecution_1() throws IOException {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("first_input.txt")).getFile();
        String[] args = {filePath};

        PortfolioOverlapApp.main(args);

        assertEquals("MIRAE_ASSET_EMERGING_BLUECHIP AXIS_BLUECHIP 39.13%\n" +
                "MIRAE_ASSET_EMERGING_BLUECHIP ICICI_PRU_BLUECHIP 38.10%\n" +
                "MIRAE_ASSET_EMERGING_BLUECHIP UTI_NIFTY_INDEX 65.52%\n" +
                "MIRAE_ASSET_LARGE_CAP AXIS_BLUECHIP 43.75%\n" +
                "MIRAE_ASSET_LARGE_CAP ICICI_PRU_BLUECHIP 44.62%\n" +
                "MIRAE_ASSET_LARGE_CAP UTI_NIFTY_INDEX 95.00%\n" +
                "MIRAE_ASSET_EMERGING_BLUECHIP AXIS_BLUECHIP 38.71%\n" +
                "MIRAE_ASSET_EMERGING_BLUECHIP ICICI_PRU_BLUECHIP 38.10%\n" +
                "MIRAE_ASSET_EMERGING_BLUECHIP UTI_NIFTY_INDEX 65.52%\n", outContent.toString());
    }

    @Test
    public void shouldStartExecution_2() throws IOException {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("second_input.txt")).getFile();
        String[] args = {filePath};

        PortfolioOverlapApp.main(args);

        assertEquals("ICICI_PRU_NIFTY_NEXT_50_INDEX UTI_NIFTY_INDEX 20.37%\n" +
                "ICICI_PRU_NIFTY_NEXT_50_INDEX AXIS_MIDCAP 14.81%\n" +
                "ICICI_PRU_NIFTY_NEXT_50_INDEX PARAG_PARIKH_FLEXI_CAP 7.41%\n" +
                "FUND_NOT_FOUND\n" +
                "ICICI_PRU_NIFTY_NEXT_50_INDEX UTI_NIFTY_INDEX 20.37%\n" +
                "ICICI_PRU_NIFTY_NEXT_50_INDEX AXIS_MIDCAP 14.68%\n" +
                "ICICI_PRU_NIFTY_NEXT_50_INDEX PARAG_PARIKH_FLEXI_CAP 7.32%\n", outContent.toString());
    }
}