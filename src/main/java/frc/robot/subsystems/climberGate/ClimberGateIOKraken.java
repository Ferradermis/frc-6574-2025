package frc.robot.subsystems.climberGate;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.util.PhoenixUtil;

public class ClimberGateIOKraken implements ClimberGateIO{
    public TalonFX motor;
    public MotionMagicVoltage request;
    private double angleSetpoint = 0;
    
    // Create a new instance of the TurretIOKraken subsystem
    // Creates a new TalonFX motor controller for the turret and a new voltage output request
    public ClimberGateIOKraken (int motorid) {
        motor = new TalonFX(motorid);
        request = new MotionMagicVoltage(0);
        configureKrakens();
        motor.setPosition(0);
    }

    // Configures the TalonFX motor controller for the turret
    public void configureKrakens() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.Voltage.PeakForwardVoltage = 6.0; //TODO: Probably need to change this value
        config.Voltage.PeakReverseVoltage = -6.0; //TODO: Probably need to change this value
        config.CurrentLimits.StatorCurrentLimit = 80.0;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 40.0;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        config.Feedback.SensorToMechanismRatio = 1; 

        PhoenixUtil.tryUntilOk(5, () -> motor.getConfigurator().apply(config));

        Slot0Configs slot0Configs = new Slot0Configs();
        slot0Configs.kP = 20.0;
        slot0Configs.kI = 0.0;
        slot0Configs.kD = 0.0;
        slot0Configs.kS = 0.0;
        slot0Configs.kG = 0.0;
        slot0Configs.kV = 0.0;
        slot0Configs.kA = 0.0;
        slot0Configs.GravityType = GravityTypeValue.Elevator_Static;
        PhoenixUtil.tryUntilOk(5, () -> motor.getConfigurator().apply(slot0Configs));

        MotionMagicConfigs motionMagicConfigs = new MotionMagicConfigs();
        motionMagicConfigs.MotionMagicCruiseVelocity = 100.0;
        motionMagicConfigs.MotionMagicAcceleration = 80.0;
        motionMagicConfigs.MotionMagicJerk = 0.0;
        motionMagicConfigs.MotionMagicExpo_kV = 0.0;
        motionMagicConfigs.MotionMagicExpo_kA = 0.0;
        
        PhoenixUtil.tryUntilOk(5, () -> motor.getConfigurator().apply(motionMagicConfigs));
    }

    // Sets the target angle of the turret
    @Override
    public void setClimberTarget(double target) {
        request = request.withPosition(target);
        motor.setControl(request);
        angleSetpoint = target;
    }

    // Updates the inputs of the turret
    @Override
    public void updateInputs(ClimberGateInputs inputs) {
        Logger.recordOutput("RobotState/ClimberGate/Position/", motor.getPosition().getValueAsDouble());
        inputs.angle = motor.getPosition().getValueAsDouble();
        Logger.recordOutput("RobotState/ClimberGate/Voltage/", motor.getMotorVoltage().getValueAsDouble());
    }

    // Stops the motor of the turret
    @Override
    public void stop() {
        motor.setControl(new StaticBrake());
    }

    // Sets the voltage of the arm
    @Override
    public void setVoltage(double voltage) {
        motor.setVoltage(voltage);
    }
}
