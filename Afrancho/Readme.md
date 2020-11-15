# Afrancho Annotation Processing

This project showcases how to setup an annotation processor locally to process annotations using gradle and google `auto-service`

`annotations` sub module defines annotations required by the project

`compiler` submodule defines the annotation processing module

`application` uses the annotations defined in `annotations` and the processor defined in `compiler` to use and process the annotations.
