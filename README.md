
# myServer
![Icon](/screenshots/icon.png?raw=true "Icon")

myServer is project written in **Kotlin** programming language and *Android* framework.

myServer is a software for controling power states on remote or local computer implementing *Wake-on-LAN* service and *SSH* protocol. Additionally user can check current power state by sending packets on *HTTP* port.

**Important note:** Target must have preconfigured *Wake-on-LAN* and *SSH*.

Download from *Play Store*: https://play.google.com/store/apps/details?id=info.sini6a.archserver

## Features
  
- Power-on-LAN by selecting port 7 or 9.  
- Power off with SSH through port 22.  
- Check power state by listening to port 80.  
- Translated in *Macedonian, Serbian, Croatian, Bosnian*  .
- Easy to use.
- Free and open source.

## Installation

**Step 1:** Open your Android Studio then go to the **File > New > Project from Version Control**.

**Step 2:** After clicking on the  **Project from Version Control** a pop-up screen will arise. In the  **Version control**  choose  **Git**  from the drop-down menu.

**Step 3:**  Then at last  **paste the link in the URL**  and choose your  **Directory**. Click on the  **Clone**  button and you are done.

Source: https://www.geeksforgeeks.org/how-to-clone-android-project-from-github-in-android-studio/

## Usage

Build from source or download precompiled from *Play Store*.

Press on the three dots in the upper right corner and open **Settings** from the menu.

Enter *IP address* and *MAC address* to enable remote **power on** and choose the right port (*WoL*).
Enter username and password to enable remote **power off** function and choose the right port (*SSH*).

In order to use power state detection, target machine must have *HTTP* service enabled and respond on port 80.

## Additional reading and instructions

Wake-on-LAN

    https://en.wikipedia.org/wiki/Wake-on-LAN
    https://www.howtogeek.com/70374/how-to-geek-explains-what-is-wake-on-lan-and-how-do-i-enable-it/

SSH

    https://en.wikipedia.org/wiki/Secure_Shell
    https://www.howtogeek.com/336775/how-to-enable-and-use-windows-10s-built-in-ssh-commands/
    https://osxdaily.com/2016/08/16/enable-ssh-mac-command-line/
    https://linuxize.com/post/how-to-enable-ssh-on-ubuntu-18-04/

## Screenshots

![Screenshot #1](/screenshots/Artboard1.png?raw=true "Screenshot #1")

![Screenshot #2](/screenshots/Artboard2.png?raw=true "Screenshot #2")

![Screenshot #3](/screenshots/Artboard3.png?raw=true "Screenshot #3")

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](LICENSE.md)

