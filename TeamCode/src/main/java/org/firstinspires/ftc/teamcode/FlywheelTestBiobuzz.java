package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Biobuzz Flywheel Test")
public class FlywheelTestBiobuzz extends LinearOpMode {
    private DcMotorEx flywheel;
    private DcMotor bottomCollection;
    private DcMotor topCollection;
    // Track the desired velocity of the flywheel; this allows incrementing/decrementing
    // the velocity and also measuring the difference between actual and desired velocity
    private double desiredFlywheelVelocity;

    /**
     * Initialize our subsystems; this should be called once at robot startup
     */
    private void initializeSubsystems() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        topCollection = hardwareMap.get(DcMotor.class, "topCollection");
        bottomCollection = hardwareMap.get(DcMotor.class, "bottomCollection");
        desiredFlywheelVelocity = 0;
    }

    /**
     * Process gamepad input for the collection subsystem. This method takes in gamepad input
     * and adjusts the top and bottom collection subsystems accordingly
     */
    private void processGamepadInputForCollectionSubsystem() {
        if (gamepad1.x) {
            bottomCollection.setPower(-1);
            topCollection.setPower(-1);
        } else if (gamepad1.left_bumper) {
            bottomCollection.setPower(1);
            topCollection.setPower(.8);
        } else {
            bottomCollection.setPower(0);
            topCollection.setPower(0);
        }
    }

    /**
     * Process gamepad input for the flywheel subsystem. This method takes in gamepad input
     * and adjusts the flywheel subsystem accordingly. It also records desired
     * and actual flywheel telemetry for observability's sake
     * ===============
     * Controls:
     * a button: Reset flywheel to the baseline velocity
     * b button: Stop the flywheel
     * dpad up: Increase the speed of the flywheel
     * dpad down: Decrease the speed of the flywheel
     */
    private void processGamepadInputForFlywheel() {
        double currentFlywheelVelocity = flywheel.getVelocity();
        if (gamepad1.a) {
            desiredFlywheelVelocity = -1500;
        } else if (gamepad1.b) {
            desiredFlywheelVelocity = 0;
        } else if (gamepad1.dpad_up) {
            desiredFlywheelVelocity = currentFlywheelVelocity - 100;
        } else if (gamepad1.dpad_down) {
            desiredFlywheelVelocity = currentFlywheelVelocity + 100;
        }
        flywheel.setVelocity(desiredFlywheelVelocity);

        // Record telemetry for the current Flywheel velocity and the desired velocity
        telemetry.addData("flywheelVelocity", flywheel.getVelocity());
        telemetry.addData("desiredFlywheelVelocity", desiredFlywheelVelocity);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initializeSubsystems();

        waitForStart();

        while (opModeIsActive()) {
            processGamepadInputForCollectionSubsystem();
            processGamepadInputForFlywheel();
        }
    }
}
