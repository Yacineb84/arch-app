const myApp = {

    // Préparation des données
    data() {
        console.log("data");
        return {
            axios: null,
            movies: null,
            movie:null,
        }
    },

    // Mise en place de l'application
    mounted() {
        console.log("Mounted ");
        this.axios = axios.create({
            baseURL: 'http://localhost:8081/api/',
            timeout: 1000,
            headers: { 'Content-Type': 'application/json' },
        });
        this.getMovies();
    },

    methods: {
        
        getMovies: function(){
			console.log("récupérationn des films");
			this.axios.get('/movies')
			.then(r => {
				console.log("Affichage des films");
				this.movies = r.data;
				console.log(this.movies);
				
			})
		},
		
		showMovie: function(m){
			console.log("Visualisation du film");
			this.movie = m;
		},
		
		editMovie: function(m){
			console.log("Modification du film");
		},
		
		deleteMovie: function(m){
			console.log("Suppression du film");
			this.axios.delete('/movies/'+ m.id)
			.then(r => {
				this.getMovies();
				console.log(movies);
				});
    
		}
    }
}

Vue.createApp(myApp).mount('#myApp');