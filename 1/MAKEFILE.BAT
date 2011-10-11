del MobileChess.ja?
del MobileChess-NoKeyPad.ja?
makefile\preverify.exe -classpath lib\cldcapi10.jar;lib\midpapi20.jar -d bin bin
cd bin
copy META-INF\MANIFEST.MF ..\MobileChess.jad
..\makefile\ZIP -r -X ..\MobileChess.jar *
echo.>> META-INF\MANIFEST.MF
echo MobileChess-NoKeyPad: true>> META-INF\MANIFEST.MF
copy META-INF\MANIFEST.MF ..\MobileChess-NoKeyPad.jad
..\makefile\ZIP -r -X ..\MobileChess-NoKeyPad.jar *
cd ..
echo.>> MobileChess.jad
makefile\SHOWSIZE MobileChess.jar "MIDlet-Jar-Size: %%d" >> MobileChess.jad
echo MIDlet-Jar-URL: MobileChess.jar>> MobileChess.jad
echo.>> MobileChess-NoKeyPad.jad
makefile\SHOWSIZE MobileChess-NoKeyPad.jar "MIDlet-Jar-Size: %%d" >> MobileChess-NoKeyPad.jad
echo MIDlet-Jar-URL: MobileChess-NoKeyPad.jar>> MobileChess-NoKeyPad.jad