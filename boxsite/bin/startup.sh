#@echo off

# ---------------------------------------------------------------------------
# Start script for the boxsite Server
# ---------------------------------------------------------------------------

BOXSITE_HOME=/root/boxsite
BOXLIB_HOME=/root/boxlib

BOXSITE_LIB=""

for file in $BOXSITE_HOME/WEB-INF/lib/*.jar
do
 BOXSITE_LIB="$BOXSITE_LIB"";"$file;
done

JSO-COMMOM_LIB=""
for file in $BOXLIB_HOME/jso-common/lib/*.jar
do
 JSO-COMMOM_LIB=$JSO-COMMOM_LIB";"$file;
done

MALLET_LIB=""
for file in $BOXLIB_HOME/mallet-2.0.6/lib/*.jar
do
 MALLET_LIB=$MALLET_LIB";"$file;
done

WEBMAGIC_LIB=""
for file in $BOXLIB_HOME/webmagic/lib/*.jar
do
 WEBMAGIC_LIB=$WEBMAGIC_LIB";"$file;
done

CLSPATH=$BOXSITE_LIB";"$JSO-COMMOM_LIB";"$MALLET_LIB";"$WEBMAGIC_LIB
#CLSPATH=$CLSPATH";"$BOXLIB_HOME/jso-common/bin
#CLSPATH=$CLSPATH";"$BOXLIB_HOME/mallet-2.0.6/bin
#CLSPATH=$CLSPATH";"$BOXLIB_HOME/webmagic/bin
#CLSPATH=$CLSPATH";"$BOXSITE_HOME/WEB-INF/classes

echo $CLSPATH

BOXSITE_MEMORY=1G
BOXSITE_ENCODING=UTF-8
CLASS=box.mgr.ProcessManager

#java -Xmx$BOXSITE_MEMORY -ea -Dfile.encoding=$BOXSITE_ENCODING %CLASS%
