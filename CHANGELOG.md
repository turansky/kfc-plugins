## 19.0.0

**BREAKING CHANGE**

Separate Vite configuration folder supported.

```
my-app/
  src/jsMain/kotlin/
  vite/
    vite.config.mjs   // custom Vite config
    myscript.js       // also will be copied
    .env              // main `.env` file 
    .env.development
    .env.production    
```

#### Migration

Move all Vite configuration files to `vite` folder.