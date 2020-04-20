
# ionalert - Android Alert Dialog
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0)

A beautiful design Android Alert Dialog, alternative of [Sweet Alert Dialog](https://github.com/pedant/sweet-alert-dialog) based on [KAlertDialog](https://github.com/TutorialsAndroid/KAlertDialog) using MaterialComponent

## Screenshot

| Screenshot | Screenshot |
|--|--|
| ![enter image description here](https://raw.githubusercontent.com/oktavianto/ionalert/master/screenshot/1.jpg) | ![enter image description here](https://raw.githubusercontent.com/oktavianto/ionalert/master/screenshot/2.jpg) |
| ![enter image description here](https://raw.githubusercontent.com/oktavianto/ionalert/master/screenshot/3.jpg) | ![enter image description here](https://raw.githubusercontent.com/oktavianto/ionalert/master/screenshot/4.jpg) |
| ![enter image description here](https://raw.githubusercontent.com/oktavianto/ionalert/master/screenshot/5.jpg) | ![enter image description here](https://raw.githubusercontent.com/oktavianto/ionalert/master/screenshot/6.jpg) |

## Gradle

Add it in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

Step 2. Add the dependency

    dependencies {
            implementation 'com.github.oktavianto:ionalert:1.0.4'
    }

## Usage
A basic message：

    new IonAlert(this)
        .setTitleText("Here's a message!")
        .show();

A title with a text under：

    new IonAlert(this)
        .setTitleText("Here's a message!")
        .setContentText("It's pretty, isn't it?")
        .show();

A error message：

    new IonAlert(this, IonAlert.ERROR_TYPE)
        .setTitleText("Oops...")
        .setContentText("Something went wrong!")
        .show();

A warning message：

    new IonAlert(this, IonAlert.WARNING_TYPE)
        .setTitleText("Are you sure?")
        .setContentText("Won't be able to recover this file!")
        .setConfirmText("Yes,delete it!")
        .show();

A success message：

    new IonAlert(this, IonAlert.SUCCESS_TYPE)
        .setTitleText("Good job!")
        .setContentText("You clicked the button!")
        .show();

A message with a custom icon：

    new IonAlert(this, IonAlert.CUSTOM_IMAGE_TYPE)
        .setTitleText("Sweet!")
        .setContentText("Here's a custom image.")
        .setCustomImage(R.drawable.custom_img)
        .show();

Bind the listener to confirm button：

    new IonAlert(this, IonAlert.WARNING_TYPE)
        .setTitleText("Are you sure?")
        .setContentText("Won't be able to recover this file!")
        .setConfirmText("Yes,delete it!")
        .setConfirmClickListener(new IonAlert.ClickListener() {
            @Override
            public void onClick(IonAlert sDialog) {
                sDialog.dismissWithAnimation();
            }
        })
        .show();

Show the cancel button and bind listener to it：

    new IonAlert(this, IonAlert.WARNING_TYPE)
        .setTitleText("Are you sure?")
        .setContentText("Won't be able to recover this file!")
        .setCancelText("No,cancel plx!")
        .setConfirmText("Yes,delete it!")
        .showCancelButton(true)
        .setCancelClickListener(new IonAlert.ClickListener() {
            @Override
            public void onClick(IonAlert sDialog) {
                sDialog.cancel();
            }
        })
        .show();
 
     
And if you want to hide Title Text and Content Text of alert dialog

    .setTitleText("Are you sure?") //just don't write this line if you want to hide title text
    .setContentText("Won't be able to recover this file!") // don't write this line if you want to hide content text

**Change** the dialog style upon confirming：

    new IonAlert(this, IonAlert.WARNING_TYPE)
        .setTitleText("Are you sure?")
        .setContentText("Won't be able to recover this file!")
        .setConfirmText("Yes,delete it!")
        .setConfirmClickListener(new IonAlert.ClickListener() {
            @Override
            public void onClick(IonAlert sDialog) {
                sDialog
                    .setTitleText("Deleted!")
                    .setContentText("Your imaginary file has been deleted!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(null)
                    .changeAlertType(IonAlert.SUCCESS_TYPE);
            }
        })
        .show();

**Loading Spin Animation**
Using [Android-SpinKit](https://github.com/ybq/Android-SpinKit) 

    loadingDialog = IonAlert(this, IonAlert.PROGRESS_TYPE)  
            .setSpinKit("DoubleBounce")  
            .showCancelButton(true)  
            .show();
For dismiss

    loadingDialog.dismiss();
Change Loading Spin Style

    .setSpinKit("DoubleBounce")

Change Loading Spin Color

    .setSpinColor("#000000") // hex color

Style | Preview
------------     |   -------------
RotatingPlane    | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/RotatingPlane.gif' alt='RotatingPlane' width="90px" height="90px"/>
DoubleBounce     | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/DoubleBounce.gif' alt='DoubleBounce' width="90px" height="90px"/>
Wave             | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Wave.gif' alt='Wave' width="90px" height="90px"/>
WanderingCubes   | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/WanderingCubes.gif' alt='WanderingCubes' width="90px" height="90px"/>
Pulse            | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Pulse.gif' alt='Pulse' width="90px" height="90px"/>
ChasingDots      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/ChasingDots.gif' alt='ChasingDots' width="90px" height="90px"/>
ThreeBounce      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/ThreeBounce.gif' alt='ThreeBounce' width="90px" height="90px"/>
Circle           | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/Circle.gif' alt='Circle' width="90px" height="90px"/>
CubeGrid         | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/CubeGrid.gif' alt='CubeGrid' width="90px" height="90px"/>
FadingCircle     | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/FadingCircle.gif' alt='FadingCircle' width="90px" height="90px"/>
FoldingCube      | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/FoldingCube.gif' alt='FoldingCube' width="90px" height="90px"/>
RotatingCircle   | <img src='https://raw.githubusercontent.com/ybq/AndroidSpinKit/master/art/RotatingCircle.gif' alt='RotatingCircle' width="90px" height="90px"/>

**Some Properties**

Confirm Button Color

    .setConfirmButtonColor(Color.RED) // color int

Cancel Button Text Color

    .setCancelButtonColor(Color.RED) // color int

Confirm Button Text Size

    .setConfirmTextSize(int size)

Cancel Button Text Size

    .setCancelTextSize(int size)

Alert Content Text Size

    .setContentTextSize(int size)

 ## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

```
Copyright 2020 ionalert

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
       
