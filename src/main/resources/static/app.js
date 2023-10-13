import Message from './message.js';
import Counter from './counter.js';

const myApp = {

	// Préparation des données
	data() {
		console.log("data");
		return {
			axios: null,
			movies: null,
			movie: null,
			editable: null,
			add:null,
			errors: [],
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

		getMovies: function() {
			console.log("Récupération des films");
			this.axios.get('/movies')
				.then(r => {
					console.log("Affichage des films");
					this.movies = r.data;

				})
		},

		showMovie: function(m) {
			console.log("Visualisation du film");
			this.movie = m;
		},

		setEditable: function(m) {
			this.editable = m;
			this.movie = m;
		},

		submitMovie: function() {
			console.log("Modification du film");

			this.axios.put('/movies/' + this.editable.id, this.editable)
				.then(r => {
					this.errors = r.data;
					if (Object.keys(this.errors).length == 0) {
						console.log("Modif faite !");
						this.editable = null;
						this.movie = null;
						this.getMovies();
					}
				});

		},

		deleteMovie: function(m) {
			console.log("Suppression du film");
			console.log(this.$refs.count.counter);
			this.$refs.count.increment();
			//this.$refs.count.change(this.$refs.count.counter);
			this.axios.delete('/movies/' + m.id)
				.then(r => {
					this.getMovies();
				});
		},

		populateMovies: function() {
			console.log("Populate");
			this.axios.patch('/movies')
				.then(r => {
					console.log("Populate fait ! ")
					this.getMovies();
				});
		},

		listMovies: function() {
			this.movie = null;
			this.editable = null;
			this.add = null;
			this.getMovies();
		},
		
		setAdd: function(){
			this.add = {
				name:'',
				year: 0,
				description:''
			};
			this.movies = null;
		},
		
		addMovie: function(){
			console.log("Ajout d'un film");
			this.axios.post("/movies", this.add)
			.then(r => {
					console.log("Ajout fait ! ")
					this.listMovies();
				});
		},
	}
}

const app = Vue.createApp(myApp);
app.component('Message', Message);
app.component('Counter', Counter);
app.mount('#myApp');