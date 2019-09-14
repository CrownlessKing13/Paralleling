BINDIR =./bin
SRCDIR =./src
DOCDIR =./javadocs

.SUFFIXES: .java .class

${BINDIR}/%.class: ${SRCDIR}/%.java
	javac $< -cp ${BINDIR} -d ${BINDIR}

classes:${BINDIR}/Vector.class ${BINDIR}/CloudData.class ${BINDIR}/Sequential.class ${BINDIR}/ParallelSum.class ${BINDIR}/parallel.class ${BINDIR}/Assigment3.class ${BINDIR}


${BINDER}/CloudData.class: ${BINDIR}/CloudData.class ${BINDIR}/CloudData.java
${BINDER}/Vector.class: ${BINDIR}/Vector.class ${BINDIR}/Vector.java
${BINDER}/Sequential.class: ${BINDIR}/Sequential.class ${SRCDIR}/Sequential.java
${BINDER}/ParallelSum.class: ${BINDIR}/ParallelSum.class ${BINDIR}/ParallelSum.java
${BINDER}/parallel.class: ${BINDIR}/parallel.class ${SRCDIR}/parallel.java
${BINDER}/Assigment3.class: ${BINDIR}/Assigment3.class ${BINDIR}/Assigment3.java


default: classes

clean:
	rm -f ${BINDIR}/*.class

run:
	java -cp ./bin Assigment3

docs:
	javadoc -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

cleandocs:
	rm -rf ${DOCDIR}/*	rm -rf ${BINDIR}/*
