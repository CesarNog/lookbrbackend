importScripts('workbox-sw.prod.v2.1.2.js');

/**
 * DO NOT EDIT THE FILE MANIFEST ENTRY
 *
 * The method precache() does the following:
 * 1. Cache URLs in the manifest to a local cache.
 * 2. When a network request is made for any of these URLs the response
 *    will ALWAYS comes from the cache, NEVER the network.
 * 3. When the service worker changes ONLY assets with a revision change are
 *    updated, old cache entries are left as is.
 *
 * By changing the file manifest manually, your users may end up not receiving
 * new versions of files because the revision hasn't changed.
 *
 * Please use workbox-build or some other tool / approach to generate the file
 * manifest which accounts for changes to local files and update the revision
 * accordingly.
 */
const fileManifest = [
  {
    "url": "app/global.e2b7cba1c126f7a56a35.bundle.js",
    "revision": "b2d5f1ff2d9a15dcb632281815340c7f"
  },
  {
    "url": "app/main.e2b7cba1c126f7a56a35.bundle.js",
    "revision": "dfe6d48cc4f118003ef9b151f186fd7c"
  },
  {
    "url": "app/manifest.e2b7cba1c126f7a56a35.bundle.js",
    "revision": "b0cfcdd772a9a2362cebe0226abac9c2"
  },
  {
    "url": "app/polyfills.e2b7cba1c126f7a56a35.bundle.js",
    "revision": "d7712f8e2f3d331bbd7d724487a99146"
  },
  {
    "url": "app/vendor.e2b7cba1c126f7a56a35.bundle.js",
    "revision": "5f2abc3fda4509a4d60d3079e98c5d51"
  },
  {
    "url": "content/1cd3a1d782e85ba37677c1a2099bc002.png",
    "revision": "1cd3a1d782e85ba37677c1a2099bc002"
  },
  {
    "url": "content/912ec66d7572ff821749319396470bde.svg",
    "revision": "912ec66d7572ff821749319396470bde"
  },
  {
    "url": "content/a30deb26b4eb1521433021e326cbcc2c.png",
    "revision": "a30deb26b4eb1521433021e326cbcc2c"
  },
  {
    "url": "content/ca854e6d0785ba4b9d715049c0bdbcb3.png",
    "revision": "ca854e6d0785ba4b9d715049c0bdbcb3"
  },
  {
    "url": "global-sass.e2b7cba1c126f7a56a35.css",
    "revision": "4cf82856ad30513453c9c66354564721"
  },
  {
    "url": "i18n/en.json",
    "revision": "0c3c49a8fdb516c3a894d02486d92c31"
  },
  {
    "url": "i18n/pt-br.json",
    "revision": "ae13b6a812a810b4d01f73130da064ce"
  },
  {
    "url": "index.html",
    "revision": "251df2539378d5910ea388fe214c5f64"
  },
  {
    "url": "main-sass.e2b7cba1c126f7a56a35.css",
    "revision": "1fb3200342ed152149315a6e5c4dbbbe"
  },
  {
    "url": "swagger-ui/dist/css/print.css",
    "revision": "8cc912f865f2a9260a466a763cb4f325"
  },
  {
    "url": "swagger-ui/dist/css/reset.css",
    "revision": "9e41060781703b7b6492b418708f2ef3"
  },
  {
    "url": "swagger-ui/dist/css/screen.css",
    "revision": "421e5f1e932e9492960bf5ee003a2bf5"
  },
  {
    "url": "swagger-ui/dist/css/style.css",
    "revision": "58f2be3ec002df70ee20e331f9f820b0"
  },
  {
    "url": "swagger-ui/dist/css/typography.css",
    "revision": "d41d8cd98f00b204e9800998ecf8427e"
  },
  {
    "url": "swagger-ui/dist/images/throbber.gif",
    "revision": "bfefe70e3951f1883a84e7bc4033fe97"
  },
  {
    "url": "swagger-ui/index.html",
    "revision": "43be0165f0293266b1cb85f65c8705ea"
  }
];

const workboxSW = new self.WorkboxSW({
  "skipWaiting": true,
  "clientsClaim": true
});
workboxSW.precache(fileManifest);
