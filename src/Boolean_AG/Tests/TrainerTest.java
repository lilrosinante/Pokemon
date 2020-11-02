package Boolean_AG.Tests;

import Boolean_AG.Pokemon.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerTest {

    Trainer player;

    @BeforeAll
    public void setUp(){

        player = new Trainer();
        player.setUsername("Peter");
    }

    @Test
    public void testTrainerEatFood(){

        player.setStamina(0);
        player.eatFood();

        assertEquals(100, player.getStamina());
    }

    @Test
    public void testTrainerChangeUsername(){
        player.changeUsername("ImGood123");
        assertEquals("ImGood123", player.getUsername());
    }

}
