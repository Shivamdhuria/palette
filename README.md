<h1 align="center">🌈 Palette</h1></br>

<p align="center">
A color picker library made in Jetpack Compose.
</p>
</br>
<p align="center">
<img src="https://github.com/Shivamdhuria/palette/blob/main/assets/LeftHigh.gif" width="25.5%"/>
<img src="https://github.com/Shivamdhuria/palette/blob/main/assets/midHigh.gif" width="25.5%"/>
<img src="https://github.com/Shivamdhuria/palette/blob/main/assets/rightHigh.gif" width="25.5%"/>
</p>

## Including in your project
[![](https://jitpack.io/v/Shivamdhuria/puck.svg)](https://jitpack.io/#Shivamdhuria/puck/0.0.1)
### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```Gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation 'com.github.Shivamdhuria:palette:Version'
}
```

## Terminology
<p align="start">
<img src="https://user-images.githubusercontent.com/10262897/152220146-277408d5-14e4-4ef7-84cd-646a17bbcae0.jpg"/>
</p>

<p align="start">
<img src="https://user-images.githubusercontent.com/10262897/152220196-80a2272a-a8aa-4596-b470-49adcac4e344.jpg"/>
</p>




## How to use
```kotlin
Palette(
       defaultColor = Color.Magenta,
       buttonSize = 210.dp,
       swatches = Presets.material(),
       innerRadius = 400f,
       strokeWidth = 120f,
       spacerRotation = 5f,
       spacerOutward = 2f,
       verticalAlignment = Middle,
       horizontalAlignment = Start,
       onColorSelected = { onColorSelected(it) }
     )
```
### How to change positioning of Color Picker?

The color picker can be positioned by using the combinations of vertical and horizontal alignments. For ```verticalAlignment```, use ```Top,Middle,Bottom``` and for ```horizontalAlignment```, use ```Start,Center,End```. For example to position the color picker on top left, use -
```
Palette(
        buttonSize = 210.dp,
        swatches = Presets.material(),
        verticalAlignment = VerticalAlignment.Top,
        horizontalAlignment = HorizontalAlignment.Start,
         )

```
Which would make the color picker look like this.
<p align="start">
<img src="https://user-images.githubusercontent.com/10262897/152209128-5a120494-7304-4274-8acb-e5d16df904a1.png" width="18%"/>
</p>

### How to set button properties?
```kotlin
Palette(
        defaultColor = Color.Blue,
        buttonSize = 210.dp,
        selectedArchAnimationDuration = 1000,
        ...
        )
```
The library uses a FAB button under the hood whose `defaultColor` , `buttonSize` and `selectedArchAnimationDuration` can be set. `selectedArchAnimationDuration` is the duration of the color change animation of the button when a color is picked.

### How to change colors in the Palette?

```kotlin
Palette(
       swatches = Presets.material(),
        )
```
`swatches` parameter accepts a list of *Swatches*. Each *Swatch* contains a list of colors. Ideally swatches must only shades of the same color but sometime you need to go a little crazy. 


### How to know when a color is picked?

```kotlin
Palette(
      onColorSelected = { onColorSelected(it) },
      swatches = Presets.material(),
        )
```
`onColorSelected` parameter accepts a function which is triggered when a color is picked. It also return the value of the `Color` which is picked.


#### Code & Issues
If you are a developer and you wish to contribute to the app please raise an issue, discuss, fork and submit a pull request. 
Follow [Github Flow](https://help.github.com/articles/github-flow/) for collaboration!

### Find this repository useful? :heart:
Support it by joining starring this repository. :star: <br>
And follow me on [Medium](https://medium.com/@shivamdhuria), [Github](https://github.com/Shivamdhuria) and [Twitter](https://twitter.com/shivamdhuria27)

### Project Maintainers
This project is founded and actively maintained by [Shivam Dhuria](https://github.com/Shivamdhuria). 


# License
```xml
Copyright 2021 Shivam Dhuria

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
