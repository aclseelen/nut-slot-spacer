# Nut Slot Spacer

*NOTE: This software and documentation was created by [Alfons Seelen](https://github.com/sponsors/aclseelen). If you consider this code or application helpful, please consider a donation via the sponsor button or my **GitHub sponsor profile** linked above. This would really help me out and would be a great motivator for me to continue creating free software, tools, and documentation.*

## The theory

### The reason for a more complex nut slot spacing (than just equally spaced center lines)

Strings on a musical instrument (e.g. a guitar) usually have different diameters. If we space them equally based on their center, the result can look odd, as if they're not equally spaced at all. This is because the actual visible space between the strings -- i.e. "the gaps" -- get smaller as the strings get thicker. The thicker the strings (like for example a bass guitar), the more visible this phenomenon gets.

A solution to this (mostly visual) "problem", is to calculate equal spacing between all strings, that is, from string edge to string edge. As a result, the thicker the strings get, the greater the distances between the center lines get. This however, requires a little more effort in calculating the actual string center spacings.

### Nut edge to outer string edge or to outer string center

If you define the distances between the edges of the nut and the outer strings, it makes a (minor) difference whether you use the outer edge or the string center as a reference. Consider a very standard guitar with outer string diameters of 0.046" and 0.010" and nut edge distances of 1/8th of an inch (so, 0.125", or 3.175 mm).

When drawing string center lines to the nut (for filing slots), in the latter case (string center reference), the distance between your line and the nut edge (string edge reference), will be exactly 0.125" on both sides. In the first case however, your reference lines need to be at 0.125" plus half the string diameter from the nut edges. On the low E side (bass), the (string center) line should then be drawn at 0.148" (0.125 + 0.023) from the nut edge, while 0.130" (0.125 + 0.005) on the high E side (treble), in order to finally have the string edges exactly 0.125" away from the nut edge.

Different luthiers use different calculations regarding nut edge distance. This software offers both options as a boolean value (see: *"How it works"*).

## How it works

NOTE: This software calculates values for **equal** spacing between all strings of the instrument. So it (currently) does not take into account string pairs that should be closer together, like for example a 12-string guitar.

### Input

* List of string diameters (thousands of an inch, e.g. "46" for 0.046")
* Edge spacing bass side (mm or inch)
* Edge spacing treble side (mm or inch)
* Nut width (mm or inch)
* Center or outer edge of outer strings to nut edge (*true* for center, *false* for edge) 

The number of strings is derived from the number of string diameters entered.

### Output

* List of string center line distances from bass edge of the nut
* List of string center line distances from treble edge of the nut
* String spacing calculated/used (space between outer edges of two neighbouring strings, equal for all spacings between all strings)

## Building the/an application

Currently, the code used for the calculations is ready to be used. However, there is no application included yet. Soon, a command line app, a web app, or a Java GUI app will become available.
