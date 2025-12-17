# Deployment Guide - API Rate Limiter Service

This guide covers deploying the API Rate Limiter Service to GitHub and hosting the frontend on GitHub Pages.

## Prerequisites

- Git installed on your machine
- GitHub account
- GitHub Personal Access Token (PAT) or SSH key configured
- Node.js 18+ and npm
- Java 17+ and Maven

## Step 1: Create GitHub Repository

1. Go to https://github.com/new
2. Repository name: `API-Rate-Limiter-Service`
3. Description: "Scalable API rate-limiting service with real-time dashboard"
4. Set to **Public** (required for GitHub Pages on free tier)
5. **Do NOT** initialize with README, .gitignore, or license (we already have these)
6. Click "Create repository"

## Step 2: Push Code to GitHub

The repository has been configured with all necessary files. Run these commands:

```bash
# Navigate to project directory
cd "d:\New projects\API Rate Limiter Service"

# Initialize Git repository
git init

# Add all files
git add .

# Create initial commit
git commit -m "Initial commit: API Rate Limiter Service with Spring Boot backend and React frontend"

# Set main as default branch
git branch -M main

# Add remote origin (replace with your repository URL)
git remote add origin https://github.com/bishalthakur0/API-Rate-Limiter-Service.git

# Push to GitHub
git push -u origin main
```

**Note**: You'll be prompted for your GitHub credentials. Use your Personal Access Token as the password.

## Step 3: Enable GitHub Pages

1. Go to your repository on GitHub
2. Click **Settings** → **Pages** (in the left sidebar)
3. Under "Build and deployment":
   - Source: **GitHub Actions**
4. The workflow will automatically trigger and deploy your frontend

## Step 4: Verify Deployment

1. Go to the **Actions** tab in your repository
2. Wait for the "Deploy Frontend to GitHub Pages" workflow to complete (usually 1-2 minutes)
3. Once complete, your frontend will be available at:
   ```
   https://bishalthakur0.github.io/API-Rate-Limiter-Service/
   ```

## Running Locally

### Backend (Spring Boot)

```bash
# From project root
mvn clean install
mvn spring-boot:run
```

Backend will run on: `http://localhost:8080`

### Frontend (React)

```bash
# From project root
cd frontend
npm install
npm run dev
```

Frontend will run on: `http://localhost:5173`

**Note**: For local development, you may need to update the API base URL in the frontend code to point to `http://localhost:8080`.

## Environment Variables

No environment variables are required for the basic setup. The application uses in-memory caching by default.

### Optional: Redis Configuration

To use Redis instead of in-memory cache, add to `application.properties`:

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

## Troubleshooting

### GitHub Pages Not Deploying

- Ensure repository is **public**
- Check Actions tab for error messages
- Verify GitHub Pages is enabled in Settings

### Build Failures

- Check that all dependencies are installed
- Verify Node.js and Java versions meet requirements
- Review workflow logs in Actions tab

### Frontend Can't Connect to Backend

- Backend must be running locally or deployed separately
- Update API base URL in frontend configuration
- Check CORS settings in Spring Boot application

## Backend Deployment Options

GitHub Pages only hosts static files. To deploy the Spring Boot backend:

1. **Render** (Free tier available): https://render.com
2. **Railway** (Free tier available): https://railway.app
3. **Heroku** (Paid): https://heroku.com
4. **AWS/GCP/Azure**: For production deployments

## Updating the Deployment

After making changes:

```bash
git add .
git commit -m "Description of changes"
git push
```

The GitHub Actions workflow will automatically rebuild and redeploy the frontend.

## Additional Resources

- [GitHub Pages Documentation](https://docs.github.com/en/pages)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Vite Deployment Guide](https://vitejs.dev/guide/static-deploy.html)
