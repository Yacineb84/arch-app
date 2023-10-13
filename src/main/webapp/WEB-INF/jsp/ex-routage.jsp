<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div id="myApp">
    <div class="container">

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#/">Page 1</a>
            <a class="navbar-brand" href="#/page2">Page 2</a>
            <a class="navbar-brand" href="#/bad">Bad link</a>
        </nav>

        <component :is="currentView" />

    </div>
</div>
<script type="module">

import Page1 from './page1.js';
import Page2 from './page2.js';
import NotFound from './notFound.js';

const routes = {
  '/': Page1,
  '/page2': Page2
}

const app = Vue.createApp( {
  data() {
    return {
      currentPath: window.location.hash
    }
  },
  computed: {
    currentView() {
      return routes[this.currentPath.slice(1) || '/'] || NotFound
    }
  },
  mounted() {
    window.addEventListener('hashchange', () => {
          this.currentPath = window.location.hash
        })
  }
});

app.mount('#myApp');
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>