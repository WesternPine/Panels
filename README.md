![Pipeline](https://static.planetminecraft.com/files/resource_media/screenshot/1529/score9162262.jpg)

# Panels

Panels is an API that manipulates and manages player scoreboards to allow for easier modification of per-player scoreboards, while seeing custom tags.

## Features

| Feature | Supported |
|---------|-----------|
| Reloadable | Yes |
| Per-Player Tags & Scoreboards | Yes |
| Managed | Yes |
| Simple & Automated Modification | Yes |
| Customizable | Yes |


## What Is Panels?

Panels is an API & Plugin that can help manage and automate scoreboards for minigames, server lobies, stat-tracks, custom scoreboard messages, custom name tags, and more, per player, that is fully automated & reloadable. 

Most developers struggle with understanding scoreboards and how they work, and even when they do, there's never an effecient OR fast way of coding and implementing them into your games or servers. Panels was created as a way to help these developers, experienced and newbies alike, to quicky code in their scoreboard and nametag functionality, without any other dependencies, and with the ability to let gamemodes communicate with other plugins about when a scoreboard is being used.

## Usage

**PLEASE NOTE:** Panels **IS** a plugin, whose purpose was meant to manage ALL scoreboards on a server. This code is **NOT** intended to be [shaded](https://stackoverflow.com/questions/13620281/what-is-the-maven-shade-plugin-used-for-and-why-would-you-want-to-relocate-java) into your project, nor is it intended to be a production-ready product.

To add this project into your project, please use jitpack!

## Working With Panels

**Another Note:** Since working with scoreboards is usually niether simple or elegant, do not expect this API to be the perfect solution. Additionally, as the developer, I know and understand the various improvements that can still be made to this resource, I just haven't had the time to implement them yet. For example, instead of completely resetting a player's scoreboard, I could just clear the objectives and reset them. I have **NOT** implemented this yet because this project was intended to be a **proof of concept**, rather than a fully working & functional piece of code.

### Plugin Interactions & Panels Main Class

You may only interact with the plugin after it has been loaded (After onLoad()). Targeting the main class will help get you started. You can get the main instance simply by using `Panels.getInstance()`.

Once you get the Panels instance, You will be dealing with a couple of methods to help in the Panel management process. 

`Panels#newPlayer` should be called when you want to implement a player's panel.

`Panels#discardPlayer` should be called when you are done dealing with a player's panel.

`Panels#get` should be used when you want to retreive a player's panel. If no panel exists, a new one will be made for them.

You might also find multiple `accept` methods in the main class, these are used to apply a function to all existing panels.

### The Panel Object

As expressed previously, scoreboards are a messy part of MC. In order to properly use this API & Plugin, you will need to understand how scoreboards work. Because of this, no further documentation will be provided, and you will be required to look through the source-code and understand whats goin on, all by yourself. I will mention, sometimes running the reset method will not take affect, so it may need to be ran twice. Good luck! (lol)

# Closing

I'm sorry for the poor documentation. This project was created as a proof of conecept, and not ment to be used in any actual production environment. I have gotten it to work really consistently, smoothly, and beautifully, with little-to-no impact on performance. If you find this plugin is eating up resources and TPS, try utilizing the `reset` method less. I hope this project helps to get you started! And stay tuned for updates, I should be revisiting this project in the future.

# License

[MIT](https://choosealicense.com/)
