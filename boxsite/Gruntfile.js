module.exports = function(grunt)
{

    // project config
    grunt.initConfig(
    {
        pkg: grunt.file.readJSON('package.json'),
			
	concat: {
      options: {
        separator: ';'
      },
      dist: {
        src: ['static/data/*.js','static/js/*.js'],
        dest: 'dist/js/<%= pkg.name %>.js'
      }
    },
  
  uglify: {
	options: {
    // the banner is inserted at the top of the output
    banner: '/*! <%= pkg.name %> <%= grunt.template.today("dd-mm-yyyy") %> */\n'
	 },
	 dist: {
	   files: {
	     'dist/js/<%= pkg.name %>.min.js': ['<%= concat.dist.dest %>']
	    }
	  }
     },
     });

  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');

  //
 grunt.registerTask('default', ['concat', 'uglify']);
}