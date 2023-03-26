// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//Cool examples
//https://github.com/FRC-5013-Park-Hill-Robotics/5013-RapidReact/blob/327ccd6e2e9df3948523ffe18ca6d987c85f335e/src/main/java/frc/robot/subsystems/StatusLED.java#L40

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Lights.*;

public class Lights extends SubsystemBase {
  private AddressableLED led;
  private AddressableLEDBuffer ledBuffer;
  private int firstHueForRainbow = RAINBOW_FIRST_HUE;

  /** Creates a new Lights. */
  public Lights() {
    led = new AddressableLED(0);
    ledBuffer = new AddressableLEDBuffer(150);
    led.setLength(ledBuffer.getLength());
    setLightsRainbow();
    led.setData(ledBuffer);
    led.start();
  }

  public void setLightsOrange() {
    setAllLightsColor(ORANGE_R, ORANGE_G, ORANGE_B);
  }

  public void setLightsBlue() {
    setAllLightsColor(BLUE_R, BLUE_G, BLUE_B);
  }

  public void setLightsRainbow() {
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      final var hue = (firstHueForRainbow + (i * 180 / ledBuffer.getLength())) % 180;
      ledBuffer.setHSV(i, hue, 255, 128);
      firstHueForRainbow += 3;
      firstHueForRainbow %= 180;
    }
    led.setData(ledBuffer);
  }

  public void setEveryOtherLight() {
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      if (i % 2 == 0) // evens
      {
        ledBuffer.setRGB(i, ORANGE_R, ORANGE_G, ORANGE_B);
      } else // odds
      {
        ledBuffer.setRGB(i, BLUE_R, BLUE_G, BLUE_B);
      }
    }
    led.setData(ledBuffer);
  }

  public void setAllLightsColor(int r, int g, int b) {
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, r, g, b);
    }
    led.setData(ledBuffer);
  }

  public void setOrangeAndBlueChasing(int chaseOffset) {
    Color[] colorArray = {Color.kOrange, Color.kNavy};
    setLightsToChase(colorArray, 3, chaseOffset);
  }

  private void setLightsToChase(Color[] colors, int chaseSegmentWidth, int chaseOffset) {
    int numberOfColors = colors.length;
		int effectiveIndex;
		int colorIndex;
		int bufferLength = ledBuffer.getLength();
		for (int index = 0; index < bufferLength; index++){
			effectiveIndex = (index + chaseOffset) % bufferLength;
			colorIndex =( index /chaseSegmentWidth )% numberOfColors;
			ledBuffer.setLED(effectiveIndex, colors[colorIndex]);
		}
		chaseOffset =(chaseOffset+1) %bufferLength;
    led.setData(ledBuffer);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
 