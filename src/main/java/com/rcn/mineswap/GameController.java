package com.rcn.mineswap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("cellValue/{x}/{y}")
    String CellValue(int x, int y) {
        GameInstance instance = GameInstance.getInstance();
        return String.valueOf(instance.GetCellContent(x,y));
    }
}
