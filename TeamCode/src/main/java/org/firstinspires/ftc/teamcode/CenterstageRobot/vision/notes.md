# Notes for OpenCV
- The Scalar values determine what color you're searching for, and you typically have a lower and upper color on the scale
  - Depends on HSV or RGB, for HSV the scalar goes by (Color value, saturation, value), saturation and value usually from 100-255
~~~ java
Scalar lowerYellow = new Scalar(100, 100, 100);
Scalar upperYellow = new Scalar(180, 225, 225); 
~~~
- Largest contour: largest blob of a color, take all contours and figure out which one has the greatest area
- Formula for the distance between object and camera: 
  - Distance = (Real known width of object * focal length of camera (in pixels))/width in pixels
  - To actually calculate for this, use the real distance from the object (center) to the camera, width of the object
and the FTC Dashboard will tell you the camera pixel amount (P in denominator), P is calculated in code by OpenCV
  - This distance formula only calculates the distance correctly when the camera is in front of the object