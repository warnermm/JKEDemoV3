#!/usr/bin/env ruby


################################################################################
# Dependencies                                                                 #
################################################################################

require File.join( File.dirname(__FILE__), "..", "JKEFunctionalTests", "test_web_services")

def main
  
  Test::Unit::UI::Console::TestRunner.run(JKEFunctionalTests)
     
end

################################################################################
# Main Method                                                                  #
################################################################################
if __FILE__ == $PROGRAM_NAME
   main
   exit(0)
end