package com.rcn.mineswap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@RestController
public class GameController {

    @Autowired
    GameInstance gameInstance;
    @GetMapping("cellValue/{x}/{y}")
    String CellValue(@PathVariable Integer x, @PathVariable Integer y) {
        if (gameInstance == null) {
            gameInstance = gameInstance();
        }
        return String.valueOf(gameInstance.GetCellContent(x,y));
    }

    @PostMapping("/restart")
    Boolean Restart(HttpSession session) {
        session.invalidate();
        return true;
    }
    @Bean
    @Scope(
        value = SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GameInstance gameInstance() {
        return new GameInstance();
    }
}
