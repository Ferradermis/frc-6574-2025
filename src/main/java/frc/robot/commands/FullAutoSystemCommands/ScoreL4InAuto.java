package frc.robot.commands.FullAutoSystemCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.FullTeleopSystemCommands.ScoreLevelFour;
import frc.robot.commands.FullTeleopSystemCommands.ScoreLevelOne;

public class ScoreL4InAuto extends SequentialCommandGroup {
    public ScoreL4InAuto() {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new ScoreLevelFour().withTimeout(1)

        );
    }
}
