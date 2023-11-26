const myApp = {

	// Préparation des données
	data() {
		console.log("data");
		return {
			axios: null,
			personnes: null,
			personne: null,
			me:null,
			profil:null,
			cv:null,
			formCv:null,
			editable: null,
			addPersonne: null,
			errors: [],
			inputDisabled: true,
			isLogin: sessionStorage.getItem("token") != null,
			log: null,
		}
	},

	// Mise en place de l'application
	mounted() {
		console.log("Mounted ");
		this.axios = axios.create({
						baseURL: 'http://localhost:8081/api/',
						timeout: 1000,
						headers: { 'Content-Type': 'application/json',
									/*'Authorization': 'Bearer ' + sessionStorage.getItem("token")*/},
					});
		if(this.isLogin){	
			this.axios = axios.create({
							baseURL: 'http://localhost:8081/api/',
							timeout: 1000,
							headers: { 'Content-Type': 'application/json',
										'Authorization': 'Bearer ' + sessionStorage.getItem("token")}
						});		
			this.axios.get('/me')
					.then(r => {
						console.log("Récupération de mes informations à la reconnexion ");
						this.me = r.data.email;
						
					})
		
		}
		this.getPersonnes();
	
	},

	methods: {
		toggleInput: function(){
			this.inputDisabled = !this.inputDisabled;
		},		
		getMe: function(){
			this.axios.get('/users/' + this.me)
				.then(r => {
					console.log("Récupération de mon profil " + this.me);
					this.profil = r.data;
					console.log(this.profil);
					this.personnes = null;
					this.personne = null;
				})
		},

		getPersonnes: function() {
			console.log("Récupération des personnes");
			this.axios.get('/users')
				.then(r => {
					console.log("Affichage des personnes");
					this.personnes = r.data;

				})
		},
		
		search_person: function(){
			console.log("Recherche d'une personne");
			let input = document.getElementById('searchbar_person').value;
			if(input.length == 0) {
				input = document.getElementById('searchbar_activity').value;
				this.axios.get('/activity?search=' + input)
				.then(r => {
					console.log("Récupération des personnes avec la barre de recherche " + r.data);
					this.personnes = r.data;
				})
				
				}
			else{
				this.axios.get('/users?search=' + input)
				.then(r => {
					console.log("Récupération des personnes avec la barre de recherche " + r.data);
					this.personnes = r.data;
				})
			}
			
		},
		
		search_activity: function(){
			
		},

		showPersonne: function(p) {
			console.log("Visualisation d'une personne");
			this.axios.get('/users/' + p.email)
				.then(r => {
					console.log("Récupération de show " + p.email);
					this.cv = r.data.cv;
					this.personne = p;
				})
		},

		setEditable: function(p) {
			this.editable = p;
			//this.personne = p;
		},
		
		setFormCv: function() {
			console.log(this.profil);
			this.formCv = {} ;
		},
		
		submitActivity: function() {
			console.log("Ajout d'une activité");

			this.axios.post('/cv/' + this.profil.cv.id + "/activity", this.formCv)
				.then(r => {
					console.log(r);
					console.log(r.data);
					this.axios.post('cv/' + this.profil.cv.id + '/user/' + this.me)
						.then(res => {
							console.log(res);
							console.log(res.data)
							console.log("Modif faite !");
							this.formCv = null;
							this.profil = res.data;
							console.log(this.profil)
							
							this.getMe();
						})
						
					
				});

		},
		
		deleteActivity: function(a) {
			console.log("Suppression d'une activité");
			this.axios.delete('/activity/' + a.id)
				.then(r => {
					this.getMe();
				});
		},

		submitPersonne: function() {
			console.log("Modification d'une personne");

			this.axios.put('/users/' + this.editable.email, this.editable)
				.then(r => {
					this.errors = r.data;
					if (Object.keys(this.errors).length == 0) {
						console.log("Modif faite !");
						this.editable = null;
						this.personne = null;
						this.getPersonnes();
					}
				});

		},

		deletePersonne: function(p) {
			console.log("Suppression d'un utilisateur");
			this.axios.delete('/users/' + p.email)
				.then(r => {
					this.getPersonnes();
				});
		},

		/*populateMovies: function() {
			console.log("Populate");
			this.axios.patch('/movies')
				.then(r => {
					console.log("Populate fait ! ")
					this.getMovies();
				});
		},*/

		listPersonnes: function() {
			this.personne = null;
			this.editable = null;
			this.addPersonne = null;
			this.log = null;
			this.profil = null;
			this.getPersonnes();
		},

		setAddPersonne: function() {
			this.addPersonne = {
				email: '',
				name: '',
				firstName: '',
				site: '',
				dateOfBirth: '',
				password: ''
			};
			this.log = null;
			this.personnes = null;
		},

		submitAddPersonne: function() {
			console.log("Création d'un utilisateur");
			console.log(this.addPersonne);
			this.axios.post("/signup", this.addPersonne)
				.then(r => {
					console.log("Ajout fait ! ")
					this.listPersonnes();
				});
		},

		setLog: function() {
			this.log = {
				email: '',
				password: ''
			};
			this.personnes = null;
		},

		login: function() {
			console.log("Connexion...");
			this.axios.post("/login?email=" + this.log.email + "&password=" + this.log.password)
				.then(r => {
					console.log("Connexion réussite ! ")
					
					this.me = this.log.email;
					this.isLogin = true;
					this.log = null;
					this.axios = axios.create({
						baseURL: 'http://localhost:8081/api/',
						timeout: 1000,
						headers: { 'Content-Type': 'application/json',
									'Authorization': 'Bearer ' + r.data  },
					});
					sessionStorage.setItem("token",r.data);
					this.listPersonnes();
				});
		},
		
		logout: function() {
			console.log("Déconnexion...");
			this.axios.get("/logout")
				.then(r => {
					sessionStorage.removeItem("token");
					console.log("Déconnexion réussite ! ")
					this.isLogin = false;
					this.log = null;
					this.axios = axios.create({
						baseURL: 'http://localhost:8081/api/',
						timeout: 1000,
						headers: { 'Content-Type': 'application/json' },
					});
					this.listPersonnes();
				});
		},
	}
}

const app = Vue.createApp(myApp);
app.mount('#myApp');