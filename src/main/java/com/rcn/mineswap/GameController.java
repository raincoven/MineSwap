package com.rcn.mineswap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("cellValue/{x}/{y}")
    String CellValue(@PathVariable Integer x, @PathVariable Integer y) {
        GameInstance Instance = GameInstance.getInstance();
        return String.valueOf(Instance.GetCellContent(x,y));
    }

    @PostMapping("/restart")
    Boolean Restart() {
        GameInstance Instance = GameInstance.getInstance();
        Instance.Restart();
        return true;
    }
}
