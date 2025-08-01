package frc.robot.commands.FullAutoSystemCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Release;

public class ReleaseInAuto extends SequentialCommandGroup {
    public ReleaseInAuto() {
        // Add your commands in the addCommands() call, e.g.
        // addCommands(new FooCommand(), new BarCommand());
        addCommands(
                new Release(3.5).withTimeout(1.0)
        );
        System.out.println("ReleaseInAuto");
    }
}