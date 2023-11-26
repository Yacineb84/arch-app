<%@ include file="/WEB-INF/jsp/header.jsp"%>

<c:url var="home" value="/app" />
<c:url var="app" value="/app.js" />

<div id="myApp">
  <nav class="navbar navbar-expand-lg navva">
    <a
      style="margin-right: auto; margin-left: 0"
      class="navbar-brand"
      href="${home}"
      >Accueil</a
    >
    <a
      class="navbar-brand"
      href="#"
      v-if="isLogin"
      v-on:click="setAddPersonne()"
      >Creer un utilisateur</a
    >
    <a class="navbar-brand" href="#" v-on:click="listPersonnes()"
      >Liste des personnes</a
    >
    <a class="navbar-brand" href="#" v-if="isLogin" v-on:click="getMe()"
      >Mon Profil</a
    >

    <div id="destra">
      <div id="ricerca">
        <input
          class="inp_ricerca"
          type="text"
          id="searchbar_person"
          name="person"
          placeholder="Recherche par nom ou prenom.."
        />
        <input
          class="inp_ricerca"
          type="text"
          id="searchbar_activity"
          name="activity"
          placeholder="Recherche par activite.."
        />

        <button class="btn btn-secondary btn-sm" v-on:click="search_person()">
          Rechercher
        </button>
      </div>
      <button
        class="btn btn-primary btn-sm bottone"
        v-if="!isLogin"
        v-on:click="setLog()"
        data-toggle="modal"
        data-target="#logModal"
      >
        Se connecter
      </button>

      <button
        class="btn btn-danger btn-sm bottone"
        v-if="isLogin"
        v-on:click="logout()"
      >
        Se deconnecter
      </button>
    </div>
  </nav>

  <!-- Modal -->
  <!-- Formulaire de connexion -->
  <div class="modal fade modal-dialog modal-dialog-centered" id="logModal" v-if="(log != null)">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">Se connecter</h5>
          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="container">
            <form id="app" method="post" novalidate="true">
              <div class="form-group">
                <label>Email :</label>
                <input
                  v-model="log.email"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.email}"
                />
              </div>
              <div class="form-group">
                <label>Mot de passe :</label>
                <input
                  v-model="log.password"
                  type="password"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.passsword}"
                />
              </div>
              <div class="form-group">
                <button
                  v-on:click.prevent="login()"
                  class="btn btn-primary"
                  data-dismiss="modal"
                >
                  Connexion
                </button>
              </div>
            </form>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">
            Fermer
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- Liste des personnes -->

  <div
    class="container"
    v-if="(personne == null && log == null && profil == null)"
  >
    <h1>Liste des personnes</h1>
	<br/>

    <table class="table">
      <tr>
        <th>Nom</th>
        <th>Pr�nom</th>
        <th>Site</th>
        <th>Date de naissance</th>
        <th>Contact</th>
      </tr>
      <tr v-for="p in personnes">
        <td>{{ p.name }}</td>
        <td>{{ p.firstName }}</td>
        <td>{{ p.site }}</td>
        <td>{{ p.dateOfBirth }}</td>
        <td>{{ p.email }}</td>
        <td>
          <button class="btn btn-primary btn-sm" v-on:click="showPersonne(p)">
            Voir CV
          </button>
        </td>
      </tr>
    </table>
  </div>

  <!-- Affichage d'un CV -->

  <div class="container" v-if="(personne != null)" style="display:flex;flex-direction: column;margin-bottom: 5%;">
    <h1>CV de {{ personne.name }} {{ personne.firstName }}</h1>

    <table class="table" style="align-self: center;align-content: center;align-items: center;margin-top: 5%;">
      <tr>
        <th>Nature</th>
        <th>Ann�e</th>
        <th>Titre</th>
        <th>Description</th>
        <th>Adresse web</th>
      </tr>
      <tr v-for="a in cv.activities">
        <td>{{ a.nature }}</td>
        <td>{{ a.year }}</td>
        <td>{{ a.title }}</td>
		<td><div style="word-wrap: break-word; white-space: pre-line;">
			{{ a.description }}
		</div></td>
        <td>{{ a.webAddress }}</td>
      </tr>
    </table>
  </div>

  <!-- Affichage de son profil et de son CV -->
   <!-- Modification d'une personne -->

  <div v-if="(profil != null)" class="container" >
    <h1>Mon profil</h1>
	<span>Bonjour {{profil.firstName}}, Ici, vous pouvez modifier vos informations et votre CV</span>
      <div  style="margin-top:3%">
        <form id="app" method="post" novalidate="true">
          <div class="input-group form-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">@</span>
            </div>
            <input
              type="text"
              class="form-control"
              :disabled="true"
              v-bind:value="profil.email"
              v-model="profil.email"
            />
          </div>
          <div class="input-group form-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">NOM</span>
            </div>
            <input
              type="text"
              class="form-control"
              :disabled="inputDisabled"
              v-bind:value="profil.name"
              v-model="profil.name"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">PRENOM</span>
            </div>
            <input
              type="text"
              class="form-control"
              :disabled="inputDisabled"
              v-bind:value="profil.firstName"
              v-model="profil.firstName"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1"
                >DATE DE NAISSANCE</span
              >
            </div>
            <input
              type="text"
              class="form-control"
              :disabled="inputDisabled"
              v-bind:value="profil.dateOfBirth"
              v-model="profil.dateOfBirth"
            />
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">SITE</span>
            </div>
            <input
              type="text"
              class="form-control"
              :disabled="inputDisabled"
              v-bind:value="profil.site"
			  v-model="profil.site"
            />
          </div>
          <div style="display: flex">
            <button
              class="btn btn-warning"
              v-on:click.prevent="setEditable(profil),toggleInput()"
			  v-on:click="setEditable(profil)"
            >
              Editer
            </button>
            <div
              v-if="(inputDisabled == false)"
              style="margin-left: auto; margin-right: 0px"
            >
              <button
                style="margin-right: 3px"
                class="btn btn-success"
                v-on:click.prevent="submitPersonne(),toggleInput()"
              >
                Confirmer
              </button>
              <button class="btn btn-danger" v-on:click="toggleInput()" v-on:click.prevent="listPersonnes()">
                Annuler
              </button>
            </div>
        </form>
      </div>
    </div>
<br/><br/><br/><br/>

	<!-- MON CV -->
	<!-- Modal -->
	<div class="modal fade" id="addCV" tabindex="-1"  v-if="(formCv != null)">
		<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
			<h5 class="modal-title" id="exampleModalLabel">Ajouter CV</h5>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label>Titre</label>
					<input
					type="text"
					v-model="formCv.title"
					class="form-control"
					placeholder="Titre"
				  />
				</div>
				<div class="form-group">
					<label>Nature</label>
					<input
					v-model="formCv.nature"
					class="form-control"
					placeholder="Nature"
				/>
				</div>
				<div class="form-group">
					<label>Année</label>
					<input
					type="number"
					v-model="formCv.year"
					class="form-control"
					placeholder="Année"
				/>
				</div>
				<div class="form-group">
					<label>Description</label>
					<textarea
					type="text"
					v-model="formCv.description"
					class="form-control"
					placeholder="Description"
				></textarea>
				</div>
				<div class="form-group">
					<label>Site web</label>
					<input
					type="text"
					v-model="formCv.webAddress"
					class="form-control"
					placeholder="Site web"
				/>
				</div>
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-danger" data-dismiss="modal" v-on:click.prevent="getMe()" >Annuler</button>
			<button type="button" class="btn btn-success"  data-dismiss="modal" v-on:click.prevent="submitActivity()" >Ajouter</button>
			</div>
		</div>
		</div>
	</div>
  
    <div class="container"   style="display:flex;flex-direction: column;margin-bottom: 5%;">
      <h3 style="margin-bottom: 5%;">Mes CV</h3>
      <table class="table" style="align-self: center;align-content: center;align-items: center;">
        <tr>
          <th>Nature</th>
          <th>Ann�e</th>
          <th>Titre</th>
          <th>Description</th>
          <th>Adresse web</th>
        </tr>
        <tr v-for="a in profil.cv.activities" style="align-items: center; justify-content: center;">
          <td>{{ a.nature }}</td>
          <td>{{ a.year }}</td>
          <td>{{ a.title }}</td>
          <td>{{ a.description }}</td>
          <td>{{ a.webAddress }}</td>
          <td>
            <button
              v-on:click.prevent="deleteActivity(a)"
              class="btn btn-danger"
            >
              Supprimer
            </button>
          </td>
        </tr>
      </table>

      <button
        class="btn btn-success btn-sm"
        v-on:click="setFormCv()"
		data-toggle="modal" data-target="#addCV"
		style="margin-top:5%"
      >
        Ajouter une activité
      </button>

     <!-- <div class="container" v-if="(formCv != null)">
        <form id="app" method="post" novalidate="true">
          <table>
            <tr>
              <td>
                <input
                  v-model="formCv.nature"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.nature}"
                  placeholder="Nature"
                />
                <div v-if="(errors.name)" class="alert alert-warning">
                  {{ errors.nature }}
                </div>
              </td>
              <td>
                <input
                  v-model="formCv.year"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.year}"
                  number
                  placeholder="Ann�e"
                />
                <div v-if="(errors.year)" class="alert alert-warning">
                  {{ errors.year }}
                </div>
              </td>
              <td>
                <input
                  v-model="formCv.title"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.title}"
                  number
                  placeholder="Titre"
                />
                <div v-if="(errors.title)" class="alert alert-warning">
                  {{ errors.title }}
                </div>
              </td>
              <td>
                <input
                  v-model="formCv.description"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.description}"
                  number
                  placeholder="Description"
                />
                <div v-if="(errors.description)" class="alert alert-warning">
                  {{ errors.description }}
                </div>
              </td>
              <td>
                <input
                  v-model="formCv.webAddress"
                  class="form-control"
                  v-bind:class="{'is-invalid':errors.webAddress}"
                  number
                  placeholder="Adresse Web"
                />
                <div v-if="(errors.webAddress)" class="alert alert-warning">
                  {{ errors.webAdress }}
                </div>
              </td>
            </tr>
          </table>

          <div class="form-group">
            <button
              v-on:click.prevent="submitActivity()"
              class="btn btn-primary"
            >
              Ajouter
            </button>
            <button
              style="background-color: red; margin: 5px"
              v-on:click.prevent="getMe()"
              class="btn btn-primary"
            >
              Annuler
            </button>
          </div>
        </form>
      </div>-->
    </div>
  </div>

  <!-- Cr�ation d'un utilisateur -->

  <div class="container" v-if="(addPersonne != null)">
    <form id="app" method="post" novalidate="true">
      <div class="form-group">
        <label>Email :</label>
        <input
          v-model="addPersonne.email"
          class="form-control"
          v-bind:class="{'is-invalid':errors.email}"
        />
        <div v-if="(errors.email)" class="alert alert-warning">
          {{ errors.email }}
        </div>
      </div>
      <div class="form-group">
        <label>Nom :</label>
        <input
          v-model="addPersonne.name"
          class="form-control"
          v-bind:class="{'is-invalid':errors.name}"
          number
        />
        <div v-if="(errors.name)" class="alert alert-warning">
          {{ errors.name }}
        </div>
      </div>
      <div class="form-group">
        <label>Prénom :</label>
		<input
		v-model="addPersonne.firstName"
		class="form-control"
		v-bind:class="{'is-invalid':errors.firstName}"
		number
	  />
      </div>
      <div class="form-group">
        <label>Site :</label>
        <input v-model="addPersonne.site" class="form-control"/>
      </div>
      <div class="form-group">
        <label>Date de naissance :</label>
        <input
		type="date"
          v-model="addPersonne.dateOfBirth"
          class="form-control"
        ></input>
      </div>
      <div class="form-group">
        <label>Mot de passe :</label>
        <input
		type="password"
          v-model="addPersonne.password"
          class="form-control"
        />
      </div>
      <div class="form-group" style="display: flex;">
        <button
          v-on:click.prevent="submitAddPersonne()"
          class="btn btn-primary"
        >
          Cr�er
        </button>
        <button v-on:click="listPersonnes()" class="btn btn-danger" style="margin-right: 0px;margin-left: auto;">
          Annuler
        </button>
      </div>
    </form>
  </div>
</div>
<script src="${app}" type="module"></script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
