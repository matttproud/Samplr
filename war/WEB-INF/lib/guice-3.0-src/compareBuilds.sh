#!/bin/bash
# Compares the ant jars to the maven jars and makes sure they're the same
# (or different where/how expected)

# Build everything first.
function cleanAndBuild {
  mvn clean
  ant clean.all
  ant no_aop
  ant dist test.dist
  mvn package
  cd build/no_aop
  ant dist test.dist
  cd ../..
}

function findAndCompareJars {
  version=3.0
  for ANT in `find -name "*-rc3.jar" -path "./build/dist/*"`
  do
    if [ $ANT = "./build/dist/guice-3.0-rc3.jar" ]; then  #Check main build
      MVN=./mvn/guice-$version-rc3.jar
      extension=core
      compareJars "$ANT" "$MVN" $extension
      compareJars "./build/no_aop/build/dist/guice-3.0-rc3-no_aop.jar" "./mvn/guice-$version-rc3-no_aop.jar" "no_aop: $extension"  #also compare no_aop core
    else  # Check extensions
      extension=`echo $ANT | awk -F"-" '{print $2 }'` 
      MVN=./mvn/guice-$extension-$version-rc3.jar  
      compareJars "$ANT" "$MVN" $extension
    fi

  done;
}

function compareJars {
  ANT=$1
  MVN=$2
  extension=$3
 
  echo Comparing $3
  mkdir tmp$$
  cp $ANT tmp$$/ant.jar
  cp $MVN tmp$$/mvn.jar
  cd tmp$$   
  mkdir ant
  mkdir mvn
  cd ant
  jar -xf ../ant.jar
  cd ..
  cd mvn
  jar -xf ../mvn.jar
  cd ..
  
  # ant puts LICENSE & NOTICE files in a different place
  echo LICENSE > excludes
  echo NOTICE >> excludes
  # ant does not create DEPENDENCIES
  echo DEPENDENCIES >> excludes
  # ant/mvn slightly different in MANIFEST.MF
  echo MANIFEST.MF >> excludes
  # ant leaves empty directories for some jarjar'd paths --
  # we grep -v instead of exclude because we want to make sure
  # if any files in those directories exist, that they're diff'd
  diff -u --recursive -Xexcludes ant mvn | grep -v "Only in ant/com/google/inject/internal/asm: signature" | grep -v "Only in ant/com/google/inject/internal/cglib: beans" | grep -v "Only in ant/com/google/inject/internal/cglib: transform" | grep -v "Only in ant/com/google/inject/internal/cglib: util"
  cd ..
  rm -rf "tmp$$"
}

#cleanAndBuild
echo "Starting to compare jars... Check the output closely!"
echo
findAndCompareJars
echo
echo "If the only thing that printed out is 'Comparing <thing>', then you're good!"