# A sample Guardfile
# More info at https://github.com/guard/guard#readme


# notification :growl

guard "gradle-android-test", run_on_start: true do
  watch(%r{src/main/java/(.+)\.java$})
  watch(%r{src/test/java/(.+)\.java$})
end
